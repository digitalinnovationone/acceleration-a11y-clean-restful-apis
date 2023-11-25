# DIO Acceleration: Accessibility (A11y) in Clean RESTful APIs

## Overview

The Audio2Text project is an advanced system designed to transcribe audio content into text format, leveraging the power of OpenAI's API for high-quality transcriptions. The project follows Clean Architecture principles to ensure a scalable, maintainable, and well-organized codebase.

## Project Structure and References

This project's architecture is inspired by and references several key sources in Clean Architecture and software design. The structure is visualized using a mermaid graph for clarity.

```mermaid
graph RL;
  subgraph "Infrastructure";
    Web(Web & Devices) <--> Con
    UI("User Interface (UI)") <--> Pre
    DB(Databases & External Integrations) <--> Gat

    subgraph "Adapters";
      Con(Controllers) <--> UC
      Pre(Presenters) <--> UC
      Gat(Gateways) -..-> |implements| IGat

      subgraph "Use Cases";
        UC(Use Cases) <--> LA
        UC <--> IGat

        subgraph "Entities";
          LA("Learning Objects (LOs)")
          LA -.- LOA[<em>Metadata includes:\n Transcript, Language,\n Revision, License etc.</em>]
          IGat(Gateway Interfaces)
          IGat -.- IRep(<em>Any external integrations:\n Database, WebClient,\n Producer etc.</em>)
        end
     end
    end
  end

classDef infra fill:#a3c9ff,stroke:#00315c,color:#00315c;
classDef adapters fill:#67dbb1,stroke:#003828,color:#003828;
classDef ucs fill:#ffb1c1,stroke:#5f112b,color:#5f112b;
classDef entities fill:#e2c54b,stroke:#3a3000,color:#3a3000;
classDef entities_secondary fill:#fff0c0,stroke:#3a3000,color:#3a3000;

class Web,Dev,UI,DB,EXT infra;
class Con,Gat,Pre adapters;
class UC ucs;
class LA,IGat entities;
class LOA,IRep entities_secondary;

```

### Key References and Inspirations:

- **[Speech2Learning](https://github.com/falvojr/speech2learning)**: PhD project by [@falvojr](https://github.com/falvojr), which explores speech-to-text services to improve the accessibility of learning objects.
- **[The Clean Architecture Blog Post](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)**: Robert C. Martin's (Uncle Bob) seminal post on Clean Architecture principles.
- **[Clean Architecture for Spring Application](https://github.com/LuanPSantos/Clean-Architecture-For-Spring-Application)**: A comprehensive guide by LuanPSantos on implementing Clean Architecture in Spring applications.

## Development Demands (TODOs)

Our development team has outlined several key TODOs for further implementation and integration:

### Environment Setup

`TODO 1: Verify and set up the necessary environment variables for the project operation.`

### Integration with OpenAI API to High-Quality Transcription

`TODO 2: Implement the TranscriptionService contract to consume the "audio/transcription" endpoint of the OpenAI API.`

`TODO 3: Orchestrate the integration of the transcription service within the application flow.`

## Accessibility Showcase:

`TODO 4: Use JavaScript Fetch API to consume the "/transcribed-audios/{id}" endpoint and display the transcript in the front-end.`

`TODO 5: Integrate with VLibras to provide accessibility support for Brazilian Sign Language translation.`
