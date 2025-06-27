-- Sample statements for the MythOrFactLGBTQIA+ game
-- Each statement has: id, statement text, is_fact boolean, explanation, difficulty level, category

-- Difficulty Level 1 (Easy)
INSERT INTO statements (statement, is_fact, explanation, difficulty, category) VALUES
('The rainbow flag is the most widely recognized symbol of the LGBT+ community.', true, 'The rainbow flag, designed by Gilbert Baker in 1978, has become the most widely recognized and used symbol of the LGBT+ community worldwide. It represents the diversity of the community with each color having its own meaning.', 1, 'Symbols'),

('LGBT+ Pride Month is celebrated in June in many countries to commemorate the Stonewall riots.', true, 'June was chosen as Pride Month to commemorate the Stonewall riots that occurred at the end of June 1969 in New York City, which were a crucial turning point for the LGBT+ rights movement.', 1, 'History'),

('Being LGBT+ is a choice that people make.', false, 'Scientific consensus indicates that sexual orientation and gender identity are natural aspects of human diversity. Major medical and psychological organizations recognize that being LGBT+ is not a choice or something that can be changed through intervention.', 1, 'Identity'),

('Same-sex marriage is legal throughout the United States.', true, 'Same-sex marriage has been legal nationwide in the United States since the Supreme Court ruling in Obergefell v. Hodges on June 26, 2015, which guaranteed the fundamental right to marry to same-sex couples.', 1, 'Legal'),

('The terms "homosexual" and "gay" mean exactly the same thing.', false, 'While "homosexual" technically refers to sexual attraction to the same gender, many in the community prefer "gay" as "homosexual" has been historically used in clinical contexts that pathologized sexual orientation. "Gay" is also sometimes used specifically for men, while "homosexual" can refer to any gender.', 1, 'Terminology'),

('Pride flags only come in one design with rainbow colors.', false, 'There are many different Pride flags representing various identities within the LGBT+ community, including the transgender flag (pink, blue, and white), bisexual flag (pink, purple, and blue), lesbian flag, pansexual flag, and many others.', 1, 'Symbols'),

('All gay men have feminine characteristics and all lesbians have masculine characteristics.', false, 'This is a harmful stereotype. LGBT+ people express their gender in diverse ways, just like anyone else. Sexual orientation and gender expression are separate aspects of identity.', 1, 'Identity'),

('The acronym LGBT stands for Lesbian, Gay, Bisexual, and Transgender.', true, 'LGBT is the basic acronym that stands for Lesbian, Gay, Bisexual, and Transgender, though it has been expanded in various ways to be more inclusive of other identities.', 1, 'Terminology'),

('Children of same-sex parents perform worse academically than children of different-sex parents.', false, 'Extensive research shows that children of same-sex parents perform just as well academically, socially, and emotionally as children of different-sex parents. The quality of parenting matters more than the parents\' sexual orientation.', 1, 'Family');

-- Difficulty Level 2 (Medium)
INSERT INTO statements (statement, is_fact, explanation, difficulty, category) VALUES
('Transgender people exist in all cultures throughout history.', true, 'Historical and anthropological evidence shows that transgender and gender-diverse people have existed across various cultures and throughout history, often with specific cultural roles and recognition, though terminology and understanding have varied greatly.', 2, 'History'),

('All bisexual people are equally attracted to men and women.', false, 'Bisexuality is the attraction to more than one gender, but this doesn\'t mean equal attraction. Many bisexual people experience varying levels of attraction to different genders, which may also fluctuate over time. The misconception of "50/50 attraction" doesn\'t reflect the diversity of bisexual experiences.', 2, 'Identity'),

('The acronym "LGBTQIA+" stands for Lesbian, Gay, Bisexual, Transgender, Queer/Questioning, Intersex, Asexual/Aromantic, with the plus representing other identities.', true, 'The acronym has expanded over time to be more inclusive. The "Q" can stand for both Queer and Questioning, while the "A" often represents both Asexual and Aromantic communities. The plus acknowledges that there are many other identities within the community.', 2, 'Terminology'),

('The pink triangle was originally a Nazi concentration camp badge for gay men.', true, 'During World War II, Nazis forced gay men in concentration camps to wear pink triangles as identification. Later, this symbol was reclaimed by the LGBT+ community as a symbol of identity and resistance against oppression.', 2, 'History'),

('Conversion therapy has been proven to be an effective way to change someone\'s sexual orientation.', false, 'Conversion therapy has been discredited by all major medical and mental health organizations worldwide. It\'s not only ineffective but has been shown to be harmful, potentially causing depression, anxiety, and increased suicide risk. Many countries and regions have banned these practices.', 2, 'Health'),

('Pansexuality and bisexuality are exactly the same thing.', false, 'While there is overlap, pansexuality typically refers to attraction regardless of gender identity, while bisexuality traditionally refers to attraction to two or more genders. However, individual definitions can vary, and some people use the terms interchangeably.', 2, 'Identity'),

('The leather flag represents the leather and kink communities within LGBT+ culture.', true, 'The leather flag, created by Tony DeBlase in 1989, represents the leather, kink, fetish, and BDSM communities. It features black and blue stripes with a white stripe and a red heart in the upper left corner.', 2, 'Symbols'),

('Coming out is a one-time event that happens once in a person\'s life.', false, 'Coming out is typically an ongoing process. LGBT+ people may come out repeatedly throughout their lives to new friends, colleagues, family members, or in new situations. It\'s not a single event but a continuous process of self-disclosure.', 2, 'Identity'),

('The Human Rights Campaign (HRC) is the largest LGBT+ advocacy organization in the United States.', true, 'The Human Rights Campaign, founded in 1980, is indeed the largest LGBT+ civil rights advocacy group and political lobbying organization in the United States, with over 3 million members and supporters.', 2, 'Organizations');

-- Difficulty Level 3 (Hard)
INSERT INTO statements (statement, is_fact, explanation, difficulty, category) VALUES
('The first Pride was a riot led primarily by transgender women of color.', true, 'The Stonewall riots of 1969, which sparked the modern Pride movement, were led in significant part by transgender women of color, including Marsha P. Johnson and Sylvia Rivera, though many different members of the community participated in the uprising.', 3, 'History'),

('Asexuality is classified as a medical disorder.', false, 'Asexuality is recognized as a sexual orientation, not a disorder. The DSM-5 specifically states that if a person identifies as asexual, they should not be diagnosed with Female Sexual Interest/Arousal Disorder or Male Hypoactive Sexual Desire Disorder based on that identification alone.', 3, 'Health'),

('The first openly LGBT+ member of the U.S. Congress was elected in the 1970s.', true, 'Kathy Kozachenko became the first openly LGBT+ elected official in the U.S. when she won a seat on the Ann Arbor, Michigan City Council in 1974. Elaine Noble became the first openly gay or lesbian candidate elected to a state legislature in 1974 in Massachusetts. In Congress, Gerry Studds came out in 1983, becoming the first openly gay member of Congress.', 3, 'Politics'),

('The "T" in LGBT+ was added to the acronym in the 1990s.', true, 'While transgender people have always been part of the broader movement, the acronym evolved from "Gay," to "Gay and Lesbian," to "LGB," and finally "LGBT" became common usage in the 1990s, reflecting the growing recognition of transgender issues within the movement.', 3, 'History'),

('Intersex conditions are extremely rare, affecting less than 0.05% of the population.', false, 'According to experts, intersex conditions affect approximately 1.7% of the population, making them about as common as having red hair. The misconception of extreme rarity stems from limited awareness and the historical medicalization of intersex conditions.', 3, 'Health'),

('The Mattachine Society was one of the first gay rights organizations in the United States.', true, 'Founded in 1950 by Harry Hay in Los Angeles, the Mattachine Society was one of the earliest sustained gay rights organizations in the United States, advocating for gay rights through education and political action during a time of intense persecution.', 3, 'Organizations'),

('The term "gaydar" has been scientifically proven to be accurate.', false, 'While some studies suggest people may pick up on subtle cues, "gaydar" as a reliable method of determining sexual orientation has not been scientifically validated. Such assumptions often rely on stereotypes and can be inaccurate and harmful.', 3, 'Identity'),

('Lavender marriages were common in Hollywood\'s Golden Age.', true, 'Lavender marriages were marriages of convenience between gay men and lesbians, or between a gay person and a straight person, often to hide homosexuality. These were particularly common in Hollywood during the Golden Age when being openly gay could end careers.', 3, 'History'),

('The bear community uses specific symbols and terminology unique to their subculture.', true, 'The bear community, which celebrates larger, hairier gay men, has developed its own symbols (like the bear flag), terminology, and cultural practices. The bear flag was created by Craig Byrnes in 1995 and features brown, orange, yellow, tan, white, gray, and black stripes.', 3, 'Symbols');

-- Difficulty Level 4 (Expert)
INSERT INTO statements (statement, is_fact, explanation, difficulty, category) VALUES
('The term "Boston marriage" historically referred to two women living together in a romantic relationship.', true, 'In late 19th and early 20th century America, "Boston marriage" was a term used to describe two women living together in what may have been romantic relationships, though they were often characterized as "passionate friendships." These arrangements provided women with independence at a time when marriage to men was the primary expected path.', 4, 'History'),

('The first country to legalize same-sex marriage was Denmark in 1989.', false, 'While Denmark was the first country to legally recognize same-sex partnerships in 1989, it was not full marriage equality. The Netherlands was actually the first country to legalize same-sex marriage in 2001, followed by Belgium in 2003.', 4, 'Legal'),

('The Diagnostic and Statistical Manual of Mental Disorders (DSM) removed homosexuality as a mental disorder in 1973.', true, 'The American Psychiatric Association voted to remove homosexuality from the DSM-II in 1973, marking a major milestone in the depathologization of homosexuality. However, it wasn\'t until 1987 that "ego-dystonic homosexuality" was completely removed from the DSM-III-R.', 4, 'Health'),

('The term "two-spirit" originated in ancient Indigenous North American cultures.', false, 'While many Indigenous North American cultures have historically recognized diverse gender identities, the term "two-spirit" itself was actually coined in 1990 at the Indigenous Lesbian and Gay International Gathering in Winnipeg as an umbrella term to reference these traditional concepts. Specific Indigenous nations have their own terms and traditions.', 4, 'Terminology'),

('The handkerchief code (flagging) originated in San Francisco after the Gold Rush when there was a shortage of women.', true, 'The handkerchief code, a system where different colored handkerchiefs signaled different sexual preferences and practices, is thought to have originated in San Francisco after the Gold Rush. With few women present, men would wear bandanas to signal their role (red for active, blue for passive) during square dances. This system was later adapted by gay leather communities in the 1970s.', 4, 'History'),

('The word "transgender" was coined in the 1960s by psychiatrist John F. Oliven.', true, 'The term "transgender" was coined by psychiatrist John F. Oliven in his 1965 book "Sexual Hygiene and Pathology" as a more accurate alternative to the then-used term "transsexual." The term gained wider usage in the 1970s and 1980s.', 4, 'Terminology'),

('The first LGBT+ character on American television appeared in the 1970s.', false, 'LGBT+ characters appeared on American television before the 1970s. For example, in 1967, the documentary "The Homosexuals" aired on CBS, and in 1968, ABC\'s "The Corner Bar" featured what is often cited as the first recurring gay character on American TV, played by Vincent Schiavelli.', 4, 'Media'),

('The last U.S. state to repeal its sodomy laws did so voluntarily before the Supreme Court ruling in Lawrence v. Texas.', false, 'Lawrence v. Texas (2003) was a landmark Supreme Court decision that struck down sodomy laws in the 14 states that still had them. No state with these laws had voluntarily repealed them immediately before this ruling. This decision effectively invalidated sodomy laws nationwide.', 4, 'Legal'),

('The Daughters of Bilitis was the first lesbian organization in the United States.', true, 'Founded in 1955 in San Francisco by Del Martin and Phyllis Lyon, the Daughters of Bilitis was the first lesbian civil and political rights organization in the United States. The name was taken from "Songs of Bilitis" by Pierre Louÿs.', 4, 'Organizations'),

('The term "friend of Dorothy" originated from "The Wizard of Oz" and became coded language for gay men.', true, 'This phrase became popular coded language among gay men, particularly in the mid-20th century, referencing Dorothy from "The Wizard of Oz." The character\'s journey and themes of not belonging resonated with many gay men, and Judy Garland became a gay icon.', 4, 'Terminology'),

('Polari was a secret language used by gay men in the UK before homosexuality was decriminalized.', true, 'Polari was a form of cant slang used in the UK by gay men, theater people, and other marginalized groups. It provided a coded way to communicate, especially important when homosexual acts were illegal in the UK before 1967.', 4, 'History'),

('The Lambda symbol was chosen as an LGBT+ symbol because of its mathematical meaning.', true, 'The Lambda symbol (λ) was chosen by the Gay Activist Alliance in 1970. In physics, lambda represents wavelength and energy, symbolizing the energy needed for change. It was the first symbol specifically chosen by gay activists for themselves.', 4, 'Symbols'),

('Stormé DeLarverie is often credited as the woman who threw the first punch at Stonewall.', true, 'Stormé DeLarverie, a biracial drag king and lesbian, is often credited with throwing the punch that started the Stonewall riots. She was a bouncer at gay bars and a significant figure in the movement, though she remained relatively unknown for much of her life.', 4, 'History'),

('The Philadelphia Pride flag was created to address racism within the LGBT+ community.', true, 'The Philadelphia Pride flag, introduced in 2017, added black and brown stripes to the traditional rainbow flag to specifically highlight people of color within the LGBT+ community and address ongoing issues of racism within LGBT+ spaces.', 4, 'Symbols'),

('Gender dysphoria replaced "Gender Identity Disorder" in the DSM-5.', true, 'In 2013, the DSM-5 replaced "Gender Identity Disorder" with "Gender Dysphoria," shifting focus from identity itself to the distress that may accompany incongruence between experienced and assigned gender, representing a significant step in depathologizing transgender identities.', 4, 'Health');

-- Additional Easy Level Questions
INSERT INTO statements (statement, is_fact, explanation, difficulty, category) VALUES
('LGBT+ people can donate blood in all countries without restrictions.', false, 'Many countries still have restrictions on blood donation from men who have sex with men, though these policies have been relaxing in recent years. Some countries have moved from lifetime bans to deferral periods, while others have eliminated restrictions entirely.', 1, 'Health'),

('The term "queer" was originally used as a slur but has been reclaimed by the community.', true, 'The word "queer" was historically used as a derogatory term, but since the 1980s it has been reclaimed by many in the LGBT+ community as an umbrella term and expression of pride. However, some people still find it offensive, so context and consent matter.', 1, 'Terminology'),

('All transgender people undergo medical transition procedures.', false, 'Not all transgender people choose to undergo medical procedures as part of their transition. Some may pursue social transition only, while others may choose various combinations of hormone therapy, surgery, or other treatments based on their individual needs.', 1, 'Health');

-- Additional Medium Level Questions
INSERT INTO statements (statement, is_fact, explanation, difficulty, category) VALUES
('The oldest known LGBT+ organization still in existence was founded in Germany.', true, 'The Scientific-Humanitarian Committee, founded in Berlin in 1897 by Magnus Hirschfeld, is considered one of the first LGBT+ rights organizations. While it was dissolved by the Nazis, it was later re-established and continues today.', 2, 'Organizations'),

('Non-binary gender identities are a recent invention of the 21st century.', false, 'Non-binary and third gender identities have existed across many cultures throughout history. Examples include hijras in India, fa\'afafine in Samoa, and various Indigenous American two-spirit traditions. The modern terminology is new, but the identities are not.', 2, 'Identity'),

('The International Day Against Homophobia, Biphobia and Transphobia is observed on May 17.', true, 'IDAHOBIT is observed annually on May 17, chosen to commemorate the date in 1990 when the World Health Organization removed homosexuality from its classification of mental disorders.', 2, 'History');

-- Additional Hard Level Questions
INSERT INTO statements (statement, is_fact, explanation, difficulty, category) VALUES
('The first Pride parade in the Southern Hemisphere took place in Australia.', true, 'The first Mardi Gras parade in Sydney, Australia, took place in 1978, making it one of the first Pride events in the Southern Hemisphere. It began as a protest march and faced significant police violence, but has grown into one of the world\'s largest Pride celebrations.', 3, 'History'),

('Magnus Hirschfeld coined the term "transsexual."', false, 'While Magnus Hirschfeld was a pioneering sexologist who worked extensively with transgender individuals in early 20th century Germany, the term "transsexual" was actually coined later by American sexologist Harry Benjamin in the 1950s.', 3, 'Terminology'),

('The original Pride flag had eight colors, not six.', true, 'Gilbert Baker\'s original 1978 rainbow flag had eight stripes, including hot pink (sex) and turquoise (art). The hot pink was dropped due to fabric availability issues, and turquoise was removed to create an even number of stripes for symmetrical display.', 3, 'Symbols');