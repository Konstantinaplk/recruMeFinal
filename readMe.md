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

### Applicant

###### GET HTTP methods to get applicant/applicants according possible request
```
localhost:8080/applicants/applicant/id/{id}
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
###### POST HTTP method to add a new applicant 
```
localhost:8080/applicants/applicant/add
```
###### PUT HTTP methods to update an existed applicant
```
localhost:8080/applicants/applicant/{id}/inactive
```
```
localhost:8080/applicants/applicant/{id}/update
```

### JobOffer
###### GET HTTP methods to get jobOffer/jobOffers according possible request
```
localhost:8080/jobOffers/region/{region}
```
```
localhost:8080/jobOffers/jobOffer/id/{id}
```
```
localhost:8080/jobOffers/company/{companyName}
```
```
localhost:8080/jobOffers/activeJobOffer
```
```
localhost:8080/jobOffers/age/from/{ageFrom}/to/{ageTo}
```
```
localhost:8080/jobOffer/skill/{skillName}
```
###### POST HTTP method to add a new jobOffer
```
localhost:8080/jobOffers/jobOffer/add
```
###### PUT HTTP methods to update an existed jobOffer
```
localhost:8080/jobOffers/jobOffer/{id}/update
```
```
localhost:8080/jobOffers/jobOffer/{id}/inactive
```
```
localhost:8080/jobOffers/jobOffer/{id}/updateSkill/{name}
```
### Skill
###### GET HTTP methods to get skill/skills according possible request
```
localhost:8080/skills/all
```
```
localhost:8080/skills/mostRequested
```
```
localhost:8080/skills/notMatchedSkillsByTheApplicants
```
###### POST HTTP method to add and delete a Skill
```
localhost:8080/skills/skill/add
```
```
localhost:8080/skills/skill/delete
```
###### PUT HTTP methods to update an existed Skill
```
localhost:8080/skills/skill/{id}
```

### Match
###### GET HTTP methods to get certain match according possible request
```
localhost:8080/match/getProposedUnfinalizedMatches
```
```
localhost:8080/match/getMostRecentMatches
```
###### POST HTTP two methods to do a manual and an automatic match. 
###### POST HTTP method to delete a match
```
localhost:8080/match/appId/{appId}/jobOffer/{jobOfferId}
```
```
localhost:8080/match/createAutomaticMatches
```
```
localhost:8080/match/{matchId}/delete
```

###### PUT HTTP methods to update an existed match
```
localhost:8080/match/{matchId}/delete
```
```
localhost:8080/match/{matchId}/finalize
```

###Prerequisites
* Java (version 1.8)
* Microsoft SqlServer (version 2019)
* Spring Boot
* Maven (version 4.00)
* Lombok
* Apache
* Postman App 
