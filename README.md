# Cribhub

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## Description

This project is a system which focuses on shared accommodation management. The intent for this management system is to have a centralized platform, which is tailored towards addressing the needs of users living in communal living space. Living in a shared accommodation often comes with a set of challenges which can have varying effects on the living experience for the roommates. Our goal is to address these challenges by streamlining tasks and improving communication. This repository contains the backend part of the project.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Building, running and testing locally](#building-running-and-testing-locally)
- [License](#license)

## Installation

To install and set up the project, you need to have Docker installed on your system. If you don't have Docker installed, you can follow the instructions below:

1. Visit the [Docker website](https://www.docker.com/get-started) and download the appropriate version of Docker for your operating system.
2. Follow the installation instructions provided by Docker to install it on your system.

Once Docker is installed, you can proceed with the following steps:

1. Clone the repository by running the following command in your terminal:

   ```
   git clone https://github.com/Cribhub/cribhub-backend
   ```

2. Navigate to the root folder of the project:

   ```
   cd cribhub-backend
   ```

3. Make sure docker is actively running on your computer.

```
docker info
```

Make sure you don't see an error at the end of the output like this

```
ERROR: Cannot connect to the Docker daemon
```

4. Run the following command to start the project using Docker Compose:
   ```
   docker-compose up
   ```
   or
   ```
   docker compose up
   ```

This will start the project and any necessary dependencies defined in the Docker Compose file.

## Usage

The project is available at https://cribhub-backend-31aa52eafdcf.herokuapp.com/

If you are running the project locally you can access it by starting the project loccally and then visiting `localhost:8080` in your web browser.

## Building, running and testing locally

1. Navigate to the root folder.

```
cd cribhub-backend
```

To Build the project:

```
mvn compile
```

To run the project:

```
docker-compose up
```
or
```
docker compose up
```

To test the project:

```
mvn test
```

## License

This project is licensed under the [MIT License](LICENSE).
