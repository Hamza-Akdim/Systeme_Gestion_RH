export interface Application {
    id?: string;
    candidateId?: string;
    jobId?: string;
    jobTitle?: string;
    status?: string;
    appliedDate?: Date;
    lastUpdated?: Date;
    interviewDate?: Date;
    interviewNotes?: string;
    feedback?: string;
    rating?: number;
    salary?: number;
    offerStatus?: string;
    offerDate?: Date;
    startDate?: Date;
    documents?: string[];
    history?: ApplicationHistory[];
}

export interface ApplicationHistory {
    date: Date;
    status: string;
    notes?: string;
    updatedBy?: string;
}

export interface ApplicationStatus {
    label: string;
    value: string;
    color?: string;
}
