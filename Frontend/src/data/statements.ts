export interface Statement {
  id: number;
  statement: string;
  is_fact: boolean;
  explanation: string;
  difficulty: number;
  category: string;
}

export const statements: Statement[] = [
  // Difficulty Level 1 (Easy)
  {
    id: 1,
    statement: "The rainbow flag is the most widely recognized symbol of the LGBT+ community.",
    is_fact: true,
    explanation: "The rainbow flag, designed by Gilbert Baker in 1978, has become the most widely recognized and used symbol of the LGBT+ community worldwide. It represents the diversity of the community with each color having its own meaning.",
    difficulty: 1,
    category: "Symbols"
  },
  {
    id: 2,
    statement: "LGBT+ Pride Month is celebrated in June in many countries to commemorate the Stonewall riots.",
    is_fact: true,
    explanation: "June was chosen as Pride Month to commemorate the Stonewall riots that occurred at the end of June 1969 in New York City, which were a crucial turning point for the LGBT+ rights movement.",
    difficulty: 1,
    category: "History"
  },
  {
    id: 3,
    statement: "Being LGBT+ is a choice that people make.",
    is_fact: false,
    explanation: "Scientific consensus indicates that sexual orientation and gender identity are natural aspects of human diversity. Major medical and psychological organizations recognize that being LGBT+ is not a choice or something that can be changed through intervention.",
    difficulty: 1,
    category: "Identity"
  },
  {
    id: 4,
    statement: "Same-sex marriage is legal throughout the United States.",
    is_fact: true,
    explanation: "Same-sex marriage has been legal nationwide in the United States since the Supreme Court ruling in Obergefell v. Hodges on June 26, 2015, which guaranteed the fundamental right to marry to same-sex couples.",
    difficulty: 1,
    category: "Legal"
  },
  {
    id: 5,
    statement: "The terms 'homosexual' and 'gay' mean exactly the same thing.",
    is_fact: false,
    explanation: "While 'homosexual' technically refers to sexual attraction to the same gender, many in the community prefer 'gay' as 'homosexual' has been historically used in clinical contexts that pathologized sexual orientation. 'Gay' is also sometimes used specifically for men, while 'homosexual' can refer to any gender.",
    difficulty: 1,
    category: "Terminology"
  },

  // Difficulty Level 2 (Medium)
  {
    id: 6,
    statement: "Transgender people exist in all cultures throughout history.",
    is_fact: true,
    explanation: "Historical and anthropological evidence shows that transgender and gender-diverse people have existed across various cultures and throughout history, often with specific cultural roles and recognition, though terminology and understanding have varied greatly.",
    difficulty: 2,
    category: "History"
  },
  {
    id: 7,
    statement: "All bisexual people are equally attracted to men and women.",
    is_fact: false,
    explanation: "Bisexuality is the attraction to more than one gender, but this doesn't mean equal attraction. Many bisexual people experience varying levels of attraction to different genders, which may also fluctuate over time. The misconception of '50/50 attraction' doesn't reflect the diversity of bisexual experiences.",
    difficulty: 2,
    category: "Identity"
  },
  {
    id: 8,
    statement: "The acronym 'LGBTQIA+' stands for Lesbian, Gay, Bisexual, Transgender, Queer/Questioning, Intersex, Asexual/Aromantic, with the plus representing other identities.",
    is_fact: true,
    explanation: "The acronym has expanded over time to be more inclusive. The 'Q' can stand for both Queer and Questioning, while the 'A' often represents both Asexual and Aromantic communities. The plus acknowledges that there are many other identities within the community.",
    difficulty: 2,
    category: "Terminology"
  },
  {
    id: 9,
    statement: "The pink triangle was originally a Nazi concentration camp badge for gay men.",
    is_fact: true,
    explanation: "During World War II, Nazis forced gay men in concentration camps to wear pink triangles as identification. Later, this symbol was reclaimed by the LGBT+ community as a symbol of identity and resistance against oppression.",
    difficulty: 2,
    category: "History"
  },
  {
    id: 10,
    statement: "Conversion therapy has been proven to be an effective way to change someone's sexual orientation.",
    is_fact: false,
    explanation: "Conversion therapy has been discredited by all major medical and mental health organizations worldwide. It's not only ineffective but has been shown to be harmful, potentially causing depression, anxiety, and increased suicide risk. Many countries and regions have banned these practices.",
    difficulty: 2,
    category: "Health"
  },

  // Difficulty Level 3 (Hard)
  {
    id: 11,
    statement: "The first Pride was a riot led primarily by transgender women of color.",
    is_fact: true,
    explanation: "The Stonewall riots of 1969, which sparked the modern Pride movement, were led in significant part by transgender women of color, including Marsha P. Johnson and Sylvia Rivera, though many different members of the community participated in the uprising.",
    difficulty: 3,
    category: "History"
  },
  {
    id: 12,
    statement: "Asexuality is classified as a medical disorder.",
    is_fact: false,
    explanation: "Asexuality is recognized as a sexual orientation, not a disorder. The DSM-5 specifically states that if a person identifies as asexual, they should not be diagnosed with Female Sexual Interest/Arousal Disorder or Male Hypoactive Sexual Desire Disorder based on that identification alone.",
    difficulty: 3,
    category: "Health"
  },
  {
    id: 13,
    statement: "The first openly LGBT+ member of the U.S. Congress was elected in the 1970s.",
    is_fact: true,
    explanation: "Kathy Kozachenko became the first openly LGBT+ elected official in the U.S. when she won a seat on the Ann Arbor, Michigan City Council in 1974. Elaine Noble became the first openly gay or lesbian candidate elected to a state legislature in 1974 in Massachusetts. In Congress, Gerry Studds came out in 1983, becoming the first openly gay member of Congress.",
    difficulty: 3,
    category: "Politics"
  },
  {
    id: 14,
    statement: "The 'T' in LGBT+ was added to the acronym in the 1990s.",
    is_fact: true,
    explanation: "While transgender people have always been part of the broader movement, the acronym evolved from 'Gay,' to 'Gay and Lesbian,' to 'LGB,' and finally 'LGBT' became common usage in the 1990s, reflecting the growing recognition of transgender issues within the movement.",
    difficulty: 3,
    category: "History"
  },
  {
    id: 15,
    statement: "Intersex conditions are extremely rare, affecting less than 0.05% of the population.",
    is_fact: false,
    explanation: "According to experts, intersex conditions affect approximately 1.7% of the population, making them about as common as having red hair. The misconception of extreme rarity stems from limited awareness and the historical medicalization of intersex conditions.",
    difficulty: 3,
    category: "Health"
  },

  // Difficulty Level 4 (Expert)
  {
    id: 16,
    statement: "The term 'Boston marriage' historically referred to two women living together in a romantic relationship.",
    is_fact: true,
    explanation: "In late 19th and early 20th century America, 'Boston marriage' was a term used to describe two women living together in what may have been romantic relationships, though they were often characterized as 'passionate friendships.' These arrangements provided women with independence at a time when marriage to men was the primary expected path.",
    difficulty: 4,
    category: "History"
  },
  {
    id: 17,
    statement: "The first country to legalize same-sex marriage was Denmark in 1989.",
    is_fact: false,
    explanation: "While Denmark was the first country to legally recognize same-sex partnerships in 1989, it was not full marriage equality. The Netherlands was actually the first country to legalize same-sex marriage in 2001, followed by Belgium in 2003.",
    difficulty: 4,
    category: "Legal"
  },
  {
    id: 18,
    statement: "The Diagnostic and Statistical Manual of Mental Disorders (DSM) removed homosexuality as a mental disorder in 1973.",
    is_fact: true,
    explanation: "The American Psychiatric Association voted to remove homosexuality from the DSM-II in 1973, marking a major milestone in the depathologization of homosexuality. However, it wasn't until 1987 that 'ego-dystonic homosexuality' was completely removed from the DSM-III-R.",
    difficulty: 4,
    category: "Health"
  },
  {
    id: 19,
    statement: "The term 'two-spirit' originated in ancient Indigenous North American cultures.",
    is_fact: false,
    explanation: "While many Indigenous North American cultures have historically recognized diverse gender identities, the term 'two-spirit' itself was actually coined in 1990 at the Indigenous Lesbian and Gay International Gathering in Winnipeg as an umbrella term to reference these traditional concepts. Specific Indigenous nations have their own terms and traditions.",
    difficulty: 4,
    category: "Terminology"
  },
  {
    id: 20,
    statement: "The handkerchief code (flagging) originated in San Francisco after the Gold Rush when there was a shortage of women.",
    is_fact: true,
    explanation: "The handkerchief code, a system where different colored handkerchiefs signaled different sexual preferences and practices, is thought to have originated in San Francisco after the Gold Rush. With few women present, men would wear bandanas to signal their role (red for active, blue for passive) during square dances. This system was later adapted by gay leather communities in the 1970s.",
    difficulty: 4,
    category: "History"
  }
];

export const getRandomStatement = (usedStatements: number[] = []): Statement | null => {
  const availableStatements = statements.filter(s => !usedStatements.includes(s.id));
  if (availableStatements.length === 0) return null;

  const randomIndex = Math.floor(Math.random() * availableStatements.length);
  return availableStatements[randomIndex];
};

export const getDifficultyColor = (difficulty: number): string => {
  switch (difficulty) {
    case 1: return "bg-green-500 hover:bg-green-600";
    case 2: return "bg-yellow-500 hover:bg-yellow-600";
    case 3: return "bg-orange-500 hover:bg-orange-600";
    case 4: return "bg-red-500 hover:bg-red-600";
    default: return "bg-gray-500 hover:bg-gray-600";
  }
};

export const getCategoryColor = (category: string): string => {
  switch (category.toLowerCase()) {
    case 'symbols': return "bg-purple-500 hover:bg-purple-600";
    case 'history': return "bg-blue-500 hover:bg-blue-600";
    case 'identity': return "bg-pink-500 hover:bg-pink-600";
    case 'legal': return "bg-indigo-500 hover:bg-indigo-600";
    case 'terminology': return "bg-teal-500 hover:bg-teal-600";
    case 'health': return "bg-emerald-500 hover:bg-emerald-600";
    case 'politics': return "bg-cyan-500 hover:bg-cyan-600";
    default: return "bg-gray-500 hover:bg-gray-600";
  }
};

export const getDifficultyLabel = (difficulty: number): string => {
  switch (difficulty) {
    case 1: return "Easy";
    case 2: return "Medium";
    case 3: return "Hard";
    case 4: return "Expert";
    default: return "Unknown";
  }
};