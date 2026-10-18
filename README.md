# LandShield BD
> Pre-Registration Land-Risk Analysis & Transaction Security System

## Live Demo & Project Video
- Live Application: https://landshield-bd.onrender.com
- Project Demo Video: [Insert your video link here]

---

## Problem Statement
In Bangladesh, land transactions are frequently prone to over-selling, duplicate sales, discrepancies in deeds and mutation records, and multiple conflicting ongoing transactions. Typically, these issues are discovered after registration, leading to severe legal and financial disputes.

## Proposed Solution
LandShield BD provides a pre-registration land-risk analysis system that cross-checks ownership records, deeds, mutations, and transaction histories to deliver actionable risk outcomes (Low, Medium, or High) with detailed reasoning before any deed registration takes place.

---

## Key Features
- Property & Ownership Verification: Validates property details and cross-checks deed and mutation consistency.
- Risk Identification: Detects transferrable balance, over-selling, double sales, and pending/conflicting transactions.
- Automated Risk Scoring Engine: Calculates risk scores (0 to 100), classifies them into Low/Medium/High risk tiers, and highlights concrete causes.
- Transaction Timeline & Internal Ledger: Automatically updates seller-buyer balances upon completing low-risk demo transactions (internal ledger only).
- Officer Dashboard: Officer authentication, flagged cases overview, "Mark as Reviewed" action, and demo transaction reset functionality.
- Static Pages & Notices: About Us, Privacy Policy, and Dummy Data Disclaimers.

---

## How the Risk Engine Works
Balance Calculation:  
Balance = Base Ownership + Completed Incoming Transfers - Completed Outgoing Transfers

Scoring Breakdown:
- Location Discrepancy: +20
- Seller Has No Balance: +60
- Deed Mismatch: +20
- Mutation Mismatch: +20
- Over-selling: +60
- Duplicate Sale: +60
- Conflicting Pending Transaction: +50  
(Maximum Risk Score: 100)

Risk Tiers:
- 50 or higher = High Risk
- 20 or higher = Medium Risk
- Below 20 = Low Risk  
(Note: Only Low Risk cases can be completed.)

---

## Demo Scenarios (Test Cases)

| # | Property | Seller / Share / Amount | Expected Outcome |
|---|---|---|---|
| 1 | KH-101 / PL-501 | Rahim Ahmed / SEG-C / 7 | High - Over-selling (Balance 3, Excess 4) |
| 2 | KH-102 / PL-502 | Nusrat Jahan / SEG-M / 5 | Medium - Mismatch in Deed and Mutation |
| 3 | KH-103 / PL-503 | Karim Hasan / SEG-B / 5 | Low - Can be completed (10 -> 5, Buyer 0 -> 5) |
| 4 | Same, again SEG-B | Karim Hasan / SEG-B | High - Duplicate Sale |
| 5 | KH-104 / PL-504 | Mahmud Hasan / SEG-X / 3 | High - Duplicate Sale |
| 6 | KH-105 / PL-505 | Salma Akter / SEG-P / 3 | High - Conflicting Pending Transaction |

---

## Technology Stack
- Backend Framework: Java 17, Spring Boot 3.4 (Spring MVC, Spring Data JPA, Spring Security)
- Frontend / Template Engine: Thymeleaf, HTML5, CSS3, Bootstrap
- Database: H2 Database (Local In-Memory), PostgreSQL (Production on Render)
- Build & Deployment: Maven, Docker, Render

---

## Demo Officer Credentials
- Username: officer
- Password: Demo@123  
(Note: For demo purposes only. Configurable via ADMIN_PASSWORD environment variable.)

---

## Local Setup Instructions
Prerequisite: JDK 17+

For Windows:
.\mvnw.cmd spring-boot:run

For Linux / macOS:
./mvnw spring-boot:run

Access via browser: http://localhost:8080 (Local data runs in-memory and resets on each application restart).

---

## Deployment (Render)
Push to GitHub Repo -> Render -> New Blueprint -> Select Repository (render.yaml) -> Apply.  
The Docker image uses PostgreSQL in the prod profile using environment variables (DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD).

---

## Synthetic Data & Limitation Disclaimer
- Dummy Data: All records are entirely fictional. No real citizens' data or government records were used.
- Internal Ledger: Demo transactions update LandShield's internal sandbox ledger only; this does not constitute legal land ownership transfer or official government mutation.
- Prototype Status: This project is a concept prototype and is not directly connected to live national land databases.

---

## AI Tools Usage Declaration
AI tools (ChatGPT and Claude) were utilized for concept refinement, architectural planning, documentation, debugging, and coding support. All AI-generated outputs were thoroughly reviewed, tested, and validated by the development team.

---

## Future Integration Vision
Designed to function as an intelligent Risk-Analysis and Decision-Support Layer built on top of existing government land administration and deed registration infrastructure, rather than replacing them.

---

## Development Team
- Team Member 1: Md.Ashraful Islam Sazal
- Team Member 2: Maimuna Siddiki

---
---

# ল্যান্ডশিল্ড বিডি (LandShield BD)
> নিবন্ধন-পূর্ব ভূমি-ঝুঁকি বিশ্লেষণ ও লেনদেন সুরক্ষা

## লাইভ ডেমো ও প্রজেক্ট ভিডিও
- লাইভ ওয়েবসাইট: https://landshield-bd.onrender.com
- প্রজেক্ট পরিচিতি ও ডেমো ভিডিও: [এখানে আপনার ভিডিওর লিংক বসিয়ে দিন]

---

## সমস্যা
বাংলাদেশে ভূমি লেনদেনে অতিরিক্ত বিক্রয়, দ্বৈত বিক্রয়, দলিল-নামজারির অসামঞ্জস্য ও একই জমিতে একাধিক চলমান লেনদেনের ঝুঁকি থাকে, যা সাধারণত নিবন্ধনের পরে ধরা পড়ে।

## প্রস্তাবিত সমাধান
নিবন্ধনের আগেই মালিকানা, দলিল, নামজারি ও হস্তান্তরের ইতিহাস মিলিয়ে ব্যাখ্যাসহ নিম্ন/মধ্যম/উচ্চ ঝুঁকির ফলাফল দেওয়া।

---

## প্রধান ফিচারসমূহ
- সম্পত্তি ও মালিকানা যাচাই: সম্পত্তি ও মালিকানা যাচাই, দলিল ও নামজারির সামঞ্জস্য পরীক্ষা।
- ঝুঁকি শনাক্তকরণ: হস্তান্তরযোগ্য মালিকানা ব্যালেন্স, অতিরিক্ত বিক্রয়, দ্বৈত বিক্রয় ও অপেক্ষমাণ/চলমান লেনদেন শনাক্তকরণ।
- অটোমেটেড রিস্ক স্কোরিং Engine: ঝুঁকির স্কোর (০-১০০), নিম্ন/মধ্যম/উচ্চ স্তর এবং সুনির্দিষ্ট কারণ বিশ্লেষণ।
- লেনদেনের সময়রেখা: নিম্ন-ঝুঁকির ডেমো লেনদেন সম্পন্ন করলে বিক্রেতা-ক্রেতার ব্যালেন্স স্বয়ংক্রিয় হালনাগাদ (শুধু অভ্যন্তরীণ খাতা)।
- অফিসার ড্যাশবোর্ড: কর্মকর্তা লগইন, চিহ্নিত কেসের কারণ, "পর্যালোচিত চিহ্নিত করুন", ডেমো লেনদেন রিসেট ফিচার।
- অন্যান্য: আমাদের সম্পর্কে/গোপনীয়তা পেজ ও ডামি-ডেটা বিজ্ঞপ্তি।

---

## ঝুঁকি ইঞ্জিন যেভাবে কাজ করে
ব্যালেন্স হিসাব:  
ব্যালেন্স = মূল মালিকানা + সম্পন্ন আগত হস্তান্তর - সম্পন্ন বহির্গামী হস্তান্তর

স্কোরিং লজিক:
- অবস্থানের অমিল: +২০
- বিক্রেতার ব্যালেন্স নেই: +৬০
- দলিলে অমিল: +২০
- নামজারিতে অমিল: +২০
- অতিরিক্ত বিক্রয়: +৬০
- দ্বৈত বিক্রয়: +৬০
- চলমান লেনদেন: +৫০  
(সর্বোচ্চ স্কোর: ১০০)

ঝুঁকির স্তর:
- ৫০ বা বেশি = উচ্চ
- ২০ বা বেশি = মধ্যম
- ২০ এর নিচে = নিম্ন  
(নোট: শুধুমাত্র নিম্ন-ঝুঁকির কেসগুলো সম্পন্ন করা যায়।)

---

## ডেমো পরিস্থিতি (Test Cases)

| # | সম্পত্তি | বিক্রেতা / অংশ / পরিমাণ | প্রত্যাশিত ফল |
|---|---|---|---|
| ১ | KH-101 / PL-501 | Rahim Ahmed / SEG-C / 7 | উচ্চ - অতিরিক্ত বিক্রয় (ব্যালেন্স ৩, বাড়তি ৪) |
| ২ | KH-102 / PL-502 | Nusrat Jahan / SEG-M / 5 | মধ্যম - দলিল ও নামজারিতে অমিল |
| ৩ | KH-103 / PL-503 | Karim Hasan / SEG-B / 5 | নিম্ন - সম্পন্ন করা যায় (১০ -> ৫, ক্রেতা ০ -> ৫) |
| ৪ | একই, আবার SEG-B | Karim Hasan / SEG-B | উচ্চ - দ্বৈত বিক্রয় |
| ৫ | KH-104 / PL-504 | Mahmud Hasan / SEG-X / 3 | উচ্চ - দ্বৈত বিক্রয় |
| ৬ | KH-105 / PL-505 | Salma Akter / SEG-P / 3 | উচ্চ - অপেক্ষমাণ লেনদেনের দ্বন্দ্ব |

---

## ব্যবহৃত প্রযুক্তি
- Backend Framework: Java 17, Spring Boot 3.4 (Spring MVC, Spring Data JPA, Spring Security)
- Frontend / Template: Thymeleaf, HTML5, CSS3, Bootstrap
- Database: H2 Database (Local), PostgreSQL (Production/Render)
- Build & Deployment: Maven, Docker, Render

---

## ডেমো কর্মকর্তা লগইন তথ্য
- ব্যবহারকারী: officer
- পাসওয়ার্ড: Demo@123  
(নোট: এটি শুধুমাত্র ডেমোর জন্য। ADMIN_PASSWORD এনভায়রনমেন্ট ভ্যারিয়েবল দিয়ে পরিবর্তনযোগ্য।)

---

## লোকালি চালানোর নিয়ম
প্রয়োজনীয় শর্ত: JDK 17+

Windows-এর জন্য:
.\mvnw.cmd spring-boot:run

Linux / macOS-এর জন্য:
./mvnw spring-boot:run

ব্রাউজারে যান: http://localhost:8080 (মেমোরি ভিত্তিক লোকাল ডেটা প্রতিবার রিস্টার্টে রিসেট হবে)।

---

## ডিপ্লয়মেন্ট (Render / Production)
GitHub Repo -> Render -> New Blueprint -> সিলেক্ট করুন repo (render.yaml) -> Apply।  
Docker ইমেজ prod প্রোফাইলে PostgreSQL ব্যবহার করে (DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD)।

---

## কৃত্রিম ডেটা ও সীমাবদ্ধতার বিজ্ঞপ্তি
- ডামি ডেটা: প্রদর্শিত সকল রেকর্ড সম্পূর্ণ কাল্পনিক। কোনো প্রকৃত নাগরিক বা সরকারি তথ্যাদি ব্যবহার করা হয়নি।
- অভ্যন্তরীণ খাতা: ডেমো লেনদেন শুধুমাত্র ল্যান্ডশিল্ডের অভ্যন্তরীণ খাতা হালনাগাদ করে; এটি কোনো আইনি হস্তান্তর বা সরকারি নামজারি নয়।
- প্রোটোটাইপ: এটি একটি কনসেপ্ট প্রোটোটাইপ এবং বর্তমানে প্রকৃত জাতীয় ভূমি ব্যবস্থার সাথে সংযুক্ত নয়।

---
## এআই টুল ব্যবহারের ঘোষণা
ধারণা পরিমার্জন, কাঠামো পরিকল্পনা, ডকুমেন্টেশন, ডিবাগিং ও কোডিং সহায়তায় ChatGPT ও Claude ব্যবহার করা হয়েছে। সব আউটপুট দল পর্যালোচনা, পরীক্ষা ও যাচাই করেছে।

---

## ভবিষ্যৎ সরকারি সংযুক্তি পরিকল্পনা
বিদ্যমান ভূমি-রেকর্ড ও নিবন্ধন ব্যবস্থার বিকল্প হিসেবে নয়, বরং তার উপরে একটি শক্তিশালী ঝুঁকি-বিশ্লেষণ ও সিদ্ধান্ত-সহায়ক স্তর (Risk Analysis Layer) হিসেবে কাজ করার জন্য পরিকল্পিত।

---

## টিম (Team)
- সদস্য ১: Md.Ashraful Islam Sazal
- সদস্য ২: Maimuna Siddiki
