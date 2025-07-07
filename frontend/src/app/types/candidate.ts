export interface Candidate {
    id?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    phone?: string;
    address?: string;
    city?: string;
    country?: string;
    postalCode?: string;
    birthDate?: Date;
    skills?: string[];
    experience?: number;
    education?: string;
    resumeUrl?: string;
    profilePicture?: string;
    status?: string;
    rating?: number;
    notes?: string;
    createdAt?: Date;
    updatedAt?: Date;
}

export interface CandidateStatus {
    label: string;
    value: string;
}

export interface Skill {
    name: string;
    level: number;
}
