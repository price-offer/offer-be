#!/bin/bash

NEW_TAG=$(git log -1 --pretty=%h)
REPO=$1
DOCKER_FILE=$2
GIT_USER_EMAIL=$3
GIT_USER_NAME=$4
TAG="${REPO}:${NEW_TAG}"
LATEST="${REPO}:latest"
BUILD_TIMESTAMP=$( date '+%F_%H:%M:%S' )
MAIN_PATH=`pwd`

# build and push container image
docker build -t "$TAG" -t "$LATEST" --build-arg VERSION="$NEW_TAG" --build-arg BUILD_TIMESTAMP="$BUILD_TIMESTAMP" -f "$DOCKER_FILE" .
docker push "$TAG"
docker push "$LATEST"

# pull Argo Rollout manifest
if [ ! -e ~/offer-rollout/application-manifests ];
then
  mkdir -p ~/offer-rollout
  cd ~/offer-rollout
  git clone git@github.com:price-offer/application-manifests.git
else
  cd ~/offer-rollout/application-manifests
  git pull origin main
fi

# commit Pod image tag
cd ~/offer-rollout/application-manifests
sed -i "s/offer-dev:.*\$/offer-dev:${NEW_TAG}/g" ./services/offer-be-rollout/rollout.yaml

git config --global user.email "$GIT_USER_EMAIL"
git config --global user.name "$GIT_USER_NAME"

git add ./services/offer-be-rollout/rollout.yaml
git commit -m "[FROM GitHub Actions] Container Image Tag was changed to ${NEW_TAG}"
git push
cd $MAIN_PATH
