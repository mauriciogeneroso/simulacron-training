# Simulacron quick training

[![Build and test](https://github.com/mauriciogeneroso/simulacron-training/actions/workflows/build-test-pipeline.yml/badge.svg)](https://github.com/mauriciogeneroso/simulacron-training/actions/workflows/build-test-pipeline.yml)

This is a quick training about how to use [simulacron](https://github.com/datastax/simulacron).

Simulacron is a native protocol server simulator that helps facilitate the testing of scenarios that are difficult to reliably reproduce in driver clients and applications.

## Slides

A PDF document is included [here](./slides/simulacron-training.pdf) with an introduction to Simulacron.

## How to run

To run the application it is required `Java 17` and `docker/docker-compose`, then just need to generate the jar and start the docker compose
```shell
./gradlew build && docker-compose up -d
```

Docker compose will build a docker image for the spring application, a build is required to generate the jar. 
`-d` is used for detached mode.

## Docs

See [Wiki](https://github.com/mauriciogeneroso/simulacron-training/wiki)!

## Exercises

Todo...