import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OfferCardComponent } from './components/OfferCardComponent';
import { OffresFilterBarComponent } from './components/OffersFilterBarComponent';
import { JobOffer, JobOfferService } from './offer.service';

@Component({
    selector: 'app-offres-dashboard',
    standalone: true,
    imports: [CommonModule, OfferCardComponent, OffresFilterBarComponent],
    template: `
        <div class="p-6 space-y-6">
            <offres-filter-bar (filtersChanged)="onFiltersChanged($event)"></offres-filter-bar>

            <div class="grid grid-cols-1 2xl:grid-cols-2 gap-6">
                <offer-card *ngFor="let offre of filteredOffres; let i = index" [offre]="offre" [index]="i" />
            </div>
        </div>
    `
})
export class OffresDashboard {
    // offres = [
    //     {
    //         id: 1,
    //         title: 'DevOps Engineer',
    //         description:
    //             'As a DevOps Engineer, you will be responsible for designing, implementing, and optimizing automation processes to enable fast, reliable, and repeatable deployments. You will play a key role in managing and evolving our cloud infrastructure to ensure the availability, performance, and security of our services. Your mission will involve close collaboration with development, QA, and operations teams to integrate DevOps best practices throughout the software lifecycle, from development to production deployment. You will set up and monitor advanced monitoring systems, manage incidents, improve architecture resilience, and optimize hosting costs. You will actively participate in evaluating and integrating new technologies to maintain a cutting-edge technical environment aligned with business needs.',
    //         contrat: 'CDI',
    //         secteur: 'SOFTWARE_DEVELOPMENT',
    //         hardSkills: [
    //             { id: 1, title: 'Docker', level: 'Intermediate', description: 'Creation and management of images, optimization of containers for production.' },
    //             { id: 2, title: 'Kubernetes', level: 'Advanced', description: 'Cluster management, service and deployment configuration, auto-scaling, and resource management.' },
    //             { id: 3, title: 'CI/CD', level: 'Advanced', description: 'Implementation of automated pipelines with Jenkins, GitLab CI, or GitHub Actions, managing tests and continuous deployments.' },
    //             { id: 4, title: 'Infrastructure as Code (IaC)', level: 'Intermediate', description: 'Using Terraform or Ansible to automate configuration and provisioning of cloud resources.' },
    //             { id: 5, title: 'Cloud Platforms', level: 'Intermediate', description: 'Experience with AWS, Azure, or GCP including network configuration, security, and managed services.' },
    //             { id: 6, title: 'Monitoring & Logging', level: 'Advanced', description: 'Implementation and optimization of tools such as Prometheus, Grafana, and ELK Stack for supervision and analysis.' },
    //             { id: 7, title: 'Security Best Practices', level: 'Intermediate', description: 'Applying security measures in cloud environments and CI/CD pipelines.' }
    //         ],
    //         status: 'Open',
    //         closingDate: '2025-09-15',
    //         taskMissions: [
    //             { id: 1, title: 'Design, implement, and maintain continuous integration and continuous deployment pipelines.' },
    //             { id: 2, title: 'Supervise and optimize production and pre-production environments.' },
    //             { id: 3, title: 'Ensure high availability and fault tolerance of critical systems.' },
    //             { id: 4, title: 'Implement proactive monitoring and alerting strategies to anticipate and resolve incidents.' },
    //             { id: 5, title: 'Collaborate with developers to integrate DevOps best practices from the design phase.' },
    //             { id: 6, title: 'Optimize cloud infrastructure for performance, security, and cost control.' },
    //             { id: 7, title: 'Contribute to the automation of manual processes to improve operational efficiency.' }
    //         ],
    //         createdAt: '2025-07-10T09:00:00Z',
    //         updatedAt: '2025-07-10T09:00:00Z'
    //     },
    //     {
    //         id: 2,
    //         title: 'Java Developer',
    //         description: 'What opportunity are we offering? ...',
    //         contrat: 'Permanent',
    //         secteur: 'SOFTWARE_DEVELOPMENT',
    //         hardSkills: [
    //             { id: 8, title: 'Murex', level: 'Advanced' },
    //             { id: 9, title: 'Java', level: 'Intermediate' }
    //         ],
    //         status: 'Open',
    //         closingDate: '2025-08-31',
    //         taskMissions: [{ id: 8, title: 'Development of Murex modules' }],
    //         createdAt: '2025-07-15T10:00:00Z',
    //         updatedAt: '2025-07-15T10:00:00Z'
    //     },
    //     {
    //         id: 3,
    //         title: 'IT Project Manager – Telecommunications',
    //         description: 'Responsible for managing telecom projects for a major operator.',
    //         contrat: 'CDI',
    //         secteur: 'TELECOMMUNICATIONS',
    //         hardSkills: [
    //             { id: 10, title: 'Project Management', level: 'Advanced' },
    //             { id: 11, title: 'Telecom', level: 'Intermediate' }
    //         ],
    //         status: 'Open',
    //         closingDate: '2025-09-15',
    //         taskMissions: [
    //             { id: 9, title: 'Project oversight' },
    //             { id: 10, title: 'Team coordination' }
    //         ],
    //         createdAt: '2025-07-20T09:00:00Z',
    //         updatedAt: '2025-07-20T09:00:00Z'
    //     },
    //     {
    //         id: 4,
    //         title: 'Data Analyst – Energy Sector',
    //         description: 'Analysis of energy data and reporting for a major industry player.',
    //         contrat: 'Fixed-term',
    //         secteur: 'ENERGY_MANAGEMENT',
    //         hardSkills: [
    //             { id: 12, title: 'Python', level: 'Advanced' },
    //             { id: 13, title: 'DataViz', level: 'Intermediate' }
    //         ],
    //         status: 'Closed',
    //         closingDate: '2025-07-31',
    //         taskMissions: [
    //             { id: 11, title: 'Data extraction' },
    //             { id: 12, title: 'Dashboard creation' }
    //         ],
    //         createdAt: '2025-06-10T14:00:00Z',
    //         updatedAt: '2025-07-01T10:00:00Z'
    //     },
    //     {
    //         id: 5,
    //         title: 'IT Support Technician',
    //         description: 'Technical assistance and user support for a large company.',
    //         contrat: 'Internship',
    //         secteur: 'IT_SUPPORT',
    //         hardSkills: [
    //             { id: 14, title: 'IT Support', level: 'Beginner' },
    //             { id: 15, title: 'Windows', level: 'Beginner' }
    //         ],
    //         status: 'Open',
    //         closingDate: '2025-10-01',
    //         taskMissions: [{ id: 13, title: 'Ticket management' }],
    //         createdAt: '2025-08-01T08:00:00Z',
    //         updatedAt: '2025-08-01T08:00:00Z'
    //     },
    //     {
    //         id: 6,
    //         title: 'Data Solutions Consultant',
    //         description: 'Supporting clients in implementing data solutions.',
    //         contrat: 'Freelance',
    //         secteur: 'DATA_SOLUTIONS',
    //         hardSkills: [
    //             { id: 16, title: 'SQL', level: 'Advanced' },
    //             { id: 17, title: 'Data Engineering', level: 'Advanced' }
    //         ],
    //         status: 'Open',
    //         closingDate: '2025-09-30',
    //         taskMissions: [{ id: 14, title: 'Consulting and expertise' }],
    //         createdAt: '2025-07-25T11:00:00Z',
    //         updatedAt: '2025-07-25T11:00:00Z'
    //     }
    // ];

    DATA_Persist_Backend = [
  {
    "title": "DevOps Engineer",
    "description": "As a DevOps Engineer, you will be responsible for designing, implementing, and optimizing automation processes to enable fast, reliable, and repeatable deployments. You will play a key role in managing and evolving our cloud infrastructure to ensure the availability, performance, and security of our services. Your mission will involve close collaboration with development, QA, and operations teams to integrate DevOps best practices throughout the software lifecycle, from development to production deployment. You will set up and monitor advanced monitoring systems, manage incidents, improve architecture resilience, and optimize hosting costs. You will actively participate in evaluating and integrating new technologies to maintain a cutting-edge technical environment aligned with business needs. In addition, you will take part in the documentation of deployment practices, contribute to knowledge sharing within the team, and coach junior engineers in adopting automation tools and methodologies. Your ability to balance technical excellence with business requirements will be essential for the success of our digital transformation.",
    "contrat": "CDI",
    "secteur": "SOFTWARE_DEVELOPMENT",
    "status": "Open",
    "closingDate": "2025-09-15",
    "taskMissions": [
      { "title": "Design, implement, and maintain continuous integration and continuous deployment pipelines." },
      { "title": "Supervise and optimize production and pre-production environments." },
      { "title": "Ensure high availability and fault tolerance of critical systems." },
      { "title": "Implement proactive monitoring and alerting strategies to anticipate and resolve incidents." },
      { "title": "Collaborate with developers to integrate DevOps best practices from the design phase." },
      { "title": "Optimize cloud infrastructure for performance, security, and cost control." },
      { "title": "Contribute to the automation of manual processes to improve operational efficiency." }
    ],
    "hardSkills": [
      { "title": "Docker", "level": "Intermediate" },
      { "title": "Kubernetes", "level": "Advanced" },
      { "title": "CI/CD", "level": "Advanced" },
      { "title": "Infrastructure as Code (IaC)", "level": "Intermediate" },
      { "title": "Cloud Platforms", "level": "Intermediate" },
      { "title": "Monitoring & Logging", "level": "Advanced" },
      { "title": "Security Best Practices", "level": "Intermediate" }
    ],
    "jobQuestionDTOS": [
      {
        "question": "How many years of experience do you have with DevOps practices?",
        "responseType": "TEXT",
        "important": "Y"
      },
      {
        "question": "Which cloud platform are you most experienced with?",
        "responseType": "SELECT",
        "important": "Y",
        "selectOptions": ["AWS", "Azure", "GCP", "Other"]
      },
      {
        "question": "Are you open to being on-call for production incidents?",
        "responseType": "SELECT",
        "important": "N",
        "selectOptions": ["Yes", "No"]
      }
    ]
  },
  {
    "title": "Java Developer",
    "description": "As a Java Developer, you will be responsible for designing and maintaining scalable, high-performance applications. You will contribute to the development of mission-critical systems, working with technologies such as Murex, Java, and Spring. You will collaborate with cross-functional teams, participate in code reviews, and ensure adherence to best practices in software engineering. Beyond development, you will help optimize system performance, troubleshoot production issues, and contribute to continuous improvement efforts. This role offers the opportunity to work in a fast-paced environment where innovation and quality are paramount.",
    "contrat": "Permanent",
    "secteur": "SOFTWARE_DEVELOPMENT",
    "status": "Open",
    "closingDate": "2025-08-31",
    "taskMissions": [
      { "title": "Development of Murex modules" },
      { "title": "Contribute to backend Java development for financial systems" },
      { "title": "Support the team in resolving technical incidents" }
    ],
    "hardSkills": [
      { "title": "Murex", "level": "Advanced" },
      { "title": "Java", "level": "Intermediate" }
    ],
    "jobQuestionDTOS": [
      {
        "question": "How many years of experience do you have with Java?",
        "responseType": "TEXT",
        "important": "Y"
      },
      {
        "question": "Have you worked with Murex before?",
        "responseType": "SELECT",
        "important": "Y",
        "selectOptions": ["Yes", "No"]
      },
      {
        "question": "Are you comfortable working in a financial services environment?",
        "responseType": "SELECT",
        "important": "N",
        "selectOptions": ["Yes", "No"]
      }
    ]
  },
  {
    "title": "IT Project Manager – Telecommunications",
    "description": "As an IT Project Manager specializing in telecommunications, you will oversee the execution of complex projects for a major operator. Your role will involve coordinating cross-functional teams, managing budgets and timelines, and ensuring alignment with strategic objectives. You will act as a bridge between stakeholders, technical teams, and business units to ensure seamless project delivery. Strong organizational and leadership skills are essential, as you will be expected to anticipate risks, propose mitigation strategies, and report regularly on project progress. This is an excellent opportunity to play a pivotal role in advancing critical telecom initiatives.",
    "contrat": "CDI",
    "secteur": "TELECOMMUNICATIONS",
    "status": "Open",
    "closingDate": "2025-09-15",
    "taskMissions": [
      { "title": "Project oversight" },
      { "title": "Team coordination" },
      { "title": "Ensure project delivery within scope, time, and budget" }
    ],
    "hardSkills": [
      { "title": "Project Management", "level": "Advanced" },
      { "title": "Telecom", "level": "Intermediate" }
    ],
    "jobQuestionDTOS": [
      {
        "question": "Have you managed telecom-related projects before?",
        "responseType": "SELECT",
        "important": "Y",
        "selectOptions": ["Yes", "No"]
      },
      {
        "question": "What is the largest project budget you have managed?",
        "responseType": "TEXT",
        "important": "N"
      },
      {
        "question": "Are you comfortable managing international teams?",
        "responseType": "SELECT",
        "important": "Y",
        "selectOptions": ["Yes", "No"]
      }
    ]
  },
  {
    "title": "Data Analyst – Energy Sector",
    "description": "As a Data Analyst in the energy sector, you will be responsible for analyzing large datasets and creating meaningful insights to support strategic decisions. You will design dashboards, generate reports, and work closely with operational teams to ensure that data-driven decision-making is integrated into daily activities. You will also contribute to forecasting, anomaly detection, and optimization efforts. This role requires a strong analytical mindset, attention to detail, and proficiency in tools such as Python and Data Visualization frameworks.",
    "contrat": "Fixed-term",
    "secteur": "ENERGY_MANAGEMENT",
    "status": "Closed",
    "closingDate": "2025-07-31",
    "taskMissions": [
      { "title": "Data extraction" },
      { "title": "Dashboard creation" },
      { "title": "Reporting and statistical analysis" }
    ],
    "hardSkills": [
      { "title": "Python", "level": "Advanced" },
      { "title": "DataViz", "level": "Intermediate" }
    ],
    "jobQuestionDTOS": [
      {
        "question": "Which data visualization tools have you used?",
        "responseType": "TEXT",
        "important": "Y"
      },
      {
        "question": "Do you have experience in the energy industry?",
        "responseType": "SELECT",
        "important": "Y",
        "selectOptions": ["Yes", "No"]
      },
      {
        "question": "How many years of experience do you have in data analytics?",
        "responseType": "TEXT",
        "important": "N"
      }
    ]
  },
  {
    "title": "IT Support Technician",
    "description": "As an IT Support Technician, you will provide technical assistance and support to users within a large organization. You will handle incidents, resolve hardware and software issues, and ensure smooth operations of IT systems. Beyond troubleshooting, you will contribute to knowledge base documentation and assist in the deployment of IT infrastructure. Excellent communication skills and patience are key, as you will interact with users of varying technical backgrounds.",
    "contrat": "Internship",
    "secteur": "IT_SUPPORT",
    "status": "Open",
    "closingDate": "2025-10-01",
    "taskMissions": [
      { "title": "Ticket management" },
      { "title": "User support and troubleshooting" },
      { "title": "Assist in hardware/software installations" }
    ],
    "hardSkills": [
      { "title": "IT Support", "level": "Beginner" },
      { "title": "Windows", "level": "Beginner" }
    ],
    "jobQuestionDTOS": [
      {
        "question": "Are you currently studying IT or related fields?",
        "responseType": "SELECT",
        "important": "Y",
        "selectOptions": ["Yes", "No"]
      },
      {
        "question": "Do you have previous experience in IT support?",
        "responseType": "TEXT",
        "important": "N"
      },
      {
        "question": "Are you available full-time during the internship period?",
        "responseType": "SELECT",
        "important": "Y",
        "selectOptions": ["Yes", "No"]
      }
    ]
  },
  {
    "title": "Data Solutions Consultant",
    "description": "As a Data Solutions Consultant, you will work closely with clients to design and implement tailored data solutions. You will analyze requirements, propose architectures, and deliver scalable solutions leveraging SQL and modern data engineering practices. You will act as a trusted advisor to clients, helping them unlock the value of their data while ensuring compliance with industry standards. This role requires strong communication skills, technical expertise, and the ability to translate business needs into technical implementations.",
    "contrat": "Freelance",
    "secteur": "DATA_SOLUTIONS",
    "status": "Open",
    "closingDate": "2025-09-30",
    "taskMissions": [
      { "title": "Consulting and expertise" },
      { "title": "Design tailored data solutions for clients" },
      { "title": "Ensure quality and compliance of delivered solutions" }
    ],
    "hardSkills": [
      { "title": "SQL", "level": "Advanced" },
      { "title": "Data Engineering", "level": "Advanced" }
    ],
    "jobQuestionDTOS": [
      {
        "question": "Which data engineering tools are you most proficient in?",
        "responseType": "TEXT",
        "important": "Y"
      },
      {
        "question": "Have you previously worked as a freelance consultant?",
        "responseType": "SELECT",
        "important": "N",
        "selectOptions": ["Yes", "No"]
      },
      {
        "question": "Are you comfortable working directly with clients?",
        "responseType": "SELECT",
        "important": "Y",
        "selectOptions": ["Yes", "No"]
      }
    ]
  }
]


    offres: JobOffer[] = [];

    filteredOffres: JobOffer[] = [];
    loading = false;

    constructor(private jobOfferService: JobOfferService) {}

    ngOnInit() {
        this.fetchJobOffers();
    }

    fetchJobOffers() {
        this.loading = true;
        this.jobOfferService.getAllJobOffers().subscribe({
            next: (data) => {
                this.offres = data;
                this.filteredOffres = [...this.offres];
                this.loading = false;
            },
            error: (err) => {
                console.error('Error fetching job offers:', err);
                this.loading = false;
            }
        });
    }

    onFiltersChanged(filters: any) {
        this.filteredOffres = this.offres.filter((offre) => {
            if (filters.keyword) {
                const searchableText = [
                    offre.title,
                    offre.description,
                    offre.secteur,
                    offre.contrat,
                    offre.status,
                    ...(offre.hardSkills?.map((skill) => skill.title) || []),
                    ...(offre.hardSkills?.map((skill) => skill.level) || []),
                    ...(offre.taskMissions?.map((mission) => mission.title) || [])
                ]
                    .join(' ')
                    .toLowerCase();

                if (!searchableText.includes(filters.keyword.toLowerCase())) {
                    return false;
                }
            }
            if (filters.contrat && offre.contrat !== filters.contrat) return false;
            if (filters.secteur && offre.secteur !== filters.secteur) return false;
            if (filters.status && offre.status !== filters.status) return false;

            return true;
        });
    }
}
