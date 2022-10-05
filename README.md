# Simulacron quick training

This is a quick training about how to use [simulacron](https://github.com/datastax/simulacron).

Simulacron is a native protocol server simulator that helps facilitate the testing of scenarios that are difficult to reliably reproduce in driver clients and applications.

## Slides

A PDF document is included [here](./slides/simulacron.ppt) with an introduction to Simulacron.

## How to run

To run the application it is required Java 17 and docker.

With this setup, just need to start the docker compose
```shell
docker-compose up
```

## Docs

The application is pretty simple and includes:
- Actuator endpoints (info, health and metrics)
   * /private/info
   * /private/health
   * /private/metrics

## Exercises

Todo...