# RecruMe Project
RecruMe Application Programming Interface (API) is the background system of
an online Recruitment Agency Services platform. It consists of three sub-
syss: Central Repository of Applicants, Jobs and Skills, Matching Services
System and Reporting Services. Based on the fact that we are not covering the
User Interface part(browser), every feature will be considered complete upon
successful return of a valid JSON response.

###Getting Started
* Run the RecruMeApplication 
* Go to Postman App and run the below instructions:
###### GET HTTP methods to read once data from an excel file and save them in DB 

```
localhost:8080/skill/readExcel 
```
```
localhost:8080/applicants/readExcel
```
```
localhost:8080/jobOffer/readExcel
```

#### POST methods for applicant, jobOffer, automaticMatch, manualMatch  

###### POST HTTP method to add a new applicant 
```
localhost:8080/applicants/add
```
###### POST HTTP method to add a new jobOffer
```
localhost:8080/jobOffer/add
```
###### POST HTTP method to add a new Skill
```
localhost:8080/skill/add
```
###### POST HTTP method to do an manual match
```
localhost:8080/match/appId/{appId}/jobOffer/{jobOfferId}
```
###### POST HTTP method to do automatic match for all existed jobOffers
```
localhost:8080/createAutomaticMatches
```

### Applicant

###### GET HTTP methods to get certain applicants according possible request
```
localhost:8080/applicants/id/{id}
```
```
localhost:8080/applicants/age/from/{ageFrom}/to/{ageTo}
```
```
localhost:8080/applicants/region/{region}
```
```
localhost:8080/applicants/lastName/{lastName}
```
```
localhost:8080/applicants/activeApplicants
```
```
localhost:8080/applicants/skill/{skillName}
```
###### PUT HTTP methods to update an existed applicant
```
localhost:8080/applicants/{id}/inactive
```
```
localhost:8080/applicants/{id}/update
```

### JobOffer
###### GET HTTP methods to get certain jobOffer according possible request
```
localhost:8080/jobOffer/region/{region}
```
```
localhost:8080/jobOffer/id/{id}
```
```
localhost:8080/jobOffer/company/{companyName}
```
```
localhost:8080/jobOffer/activeJobOffer
```
```
localhost:8080/jobOffer/age/from/{ageFrom}/to/{ageTo}
```
```
localhost:8080/jobOffer/skill/{skillName}
```
###### PUT HTTP methods to update an existed jobOffer
```
localhost:8080/jobOffer/{id}/update
```
```
localhost:8080/jobOffer/{id}/inactive
```
```
localhost:8080/jobOffer/jobOffer/{id}/updateSkill/{name}
```
### Skill
###### GET HTTP methods to get certain skill according possible request




###Prerequisites
* Java (version 1.8)
* Microsoft SqlServer (version 2019)
* Spring Boot
* Maven (version 4.00)
* Lombok
* Apache
* Postman App 
