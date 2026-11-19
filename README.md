# Currency Exchange AI

## Overview

Currency Exchange AI is a Spring Boot application that retrieves historical currency exchange rates 
using the **Frankfurter API** and uses **Ollama** to analyze the data and answer user-specific questions.

## Getting Started

### Prerequisites
- Java
- Maven
- Ollama installed
- Llama model(by choice)

### 1. Clone the repository

```bash
git clone <repository-url>
cd <project-folder>
```

### 2. Download the AI model

```bash
ollama pull <llama-model>
```

### 3. Start Ollama

```bash
ollama serve
```

Make sure Ollama is running at `http://localhost:11434`.

### 4. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

### 5. Run tests (optional)

```bash
mvn test
```

## Configuration

The application uses the following default configuration in `application.properties`:

```properties
frankfurter.base-url=https://api.frankfurter.dev/v2
ollama.base-url=http://localhost:11434
ollama.model=llama3.2
```

**Note:**
- The <ollama.model> application property must be changed based on the model you are using.
- An internet connection is required to retrieve exchange rates from the Frankfurter API. 
- Ollama must be running locally for AI functionality.
