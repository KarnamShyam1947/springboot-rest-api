-- SELECT
--     d.name,
--     count(u.id)
-- FROM
--     departments d
-- LEFT JOIN
--     users u
-- ON
--     u.department = d.name
-- GROUP BY
--     d.name

SELECT
    m.id, m.name, m.price, o.id, o.phone, o.address, o.quantity, o.total_price
FROM
    medicines m
JOIN
    orders O
ON
    m.id = o.medicine_id
WHERE
    o.user_id = 1;
