# Local LLM Setup

- **Tool:** Ollama (installed via Homebrew)
- **Model:** llama3.2 (3B)
- **API:** http://localhost:11434

## Why this model
- My machine is a Mac with an Apple M3 chip and 8 GB RAM, so a small model is needed to run smoothly.
- The 3B size (about 2 GB) leaves enough memory for the IDE, browser, and the Spring Boot app.
- Responses are fast, which suits short currency-related prompts.
- Larger models (7B/8B such as Mistral or Llama 3.1) would likely cause memory pressure on 8 GB.

## Verification
- `ollama run llama3.2 "..."` returned an answer.
- `curl http://localhost:11434` returned "Ollama is running".
- `POST /api/generate` returned a JSON response.