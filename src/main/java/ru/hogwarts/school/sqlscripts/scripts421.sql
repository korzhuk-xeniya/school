ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK (age > 16);
ALTER TABLE student
        ADD CONSTRAINT name_unique UNIQUE (name);
ALTER TABLE student
        ALTER name SET NOT NULL;
ALTER TABLE student
        ALTER age SET DEFAULT 20;
LTER TABLE faculty
        ADD CONSTRAINT color_name_unique UNIQUE (color, name);