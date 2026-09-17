# RealTimeChatApp![Build Status](https://img.shields.io/github/actions/workflow/status/yourusername/RealTimeChatApp/build.yml?branch=main)![License](https://img.shields.io/badge/license-MIT-blue)

A production‑ready real‑time chat application built with **Java 21**, **Spring Boot**, and **WebSocket/STOMP**. 
The project demonstrates clean architecture, proper error handling, logging, configuration management, and unit testing.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [WebSocket API](#websocket-api)
- [Testing](#testing)
- [Configuration](#configuration)
- [License](#license)

## Features
- Bi‑directional real‑time messaging using STOMP over WebSocket
- Message broadcasting to all connected clients
- Simple authentication via a username header (for demo purposes)
- Centralized configuration (`application.properties` & `.env.example`)
- Comprehensive logging with SLF4J
- Unit & integration tests with JUnit 5 & Spring Test

## Tech Stack
| Layer | Technology |
|-------|------------|
| Runtime | Java 21 |
| Framework | Spring Boot 3.2 |
| WebSocket | Spring Messaging (STOMP) |
| Build Tool | Gradle |
| Logging | SLF4J + Logback |
| Testing | JUnit 5, SpringBootTest |
| IDE | Any IDE supporting Java (IntelliJ, VS Code, Eclipse) |

## Installation

### Prerequisites
- JDK 21 or newer
- Gradle 8.x (wrapper included)
- Git