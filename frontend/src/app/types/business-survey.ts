export interface BusinessSurvey {
    id?: string;
    title?: string;
    description?: string;
    startDate?: Date;
    endDate?: Date;
    status?: string;
    targetAudience?: string;
    questions?: SurveyQuestion[];
    responses?: SurveyResponse[];
    createdAt?: Date;
    updatedAt?: Date;
    createdBy?: string;
    isPublic?: boolean;
    tags?: string[];
}

export interface SurveyQuestion {
    id?: string;
    text?: string;
    type?: string; // 'multiple_choice', 'rating', 'text', 'boolean'
    required?: boolean;
    options?: string[];
    order?: number;
}

export interface SurveyResponse {
    id?: string;
    surveyId?: string;
    respondentName?: string;
    respondentEmail?: string;
    respondentCompany?: string;
    submittedAt?: Date;
    answers?: SurveyAnswer[];
    satisfactionScore?: number;
    feedback?: string;
    prospectId?: string;
}

export interface SurveyAnswer {
    questionId?: string;
    value?: string | number | boolean;
}

export interface SurveyStatus {
    label: string;
    value: string;
}

export interface SurveyQuestionType {
    label: string;
    value: string;
}
