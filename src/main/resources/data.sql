INSERT INTO resource (name, url, created_at, modified_at)
SELECT 'Лента', 'https://lenta.ru/', now(), now()
WHERE NOT EXISTS (
    SELECT 1
    FROM resource
    WHERE name = 'Лента'
);

INSERT INTO resource (name, url, created_at, modified_at)
SELECT 'РИА', 'https://ria.ru/', now(), now()
WHERE NOT EXISTS (
    SELECT 1
    FROM resource
    WHERE name = 'РИА'
);