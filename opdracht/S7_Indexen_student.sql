-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S7: Indexen
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------
-- LET OP, zoals in de opdracht op Canvas ook gezegd kun je informatie over
-- het query plan vinden op: https://www.postgresql.org/docs/current/using-explain.html


-- S7.1.
--
-- Je maakt alle opdrachten in de 'sales' database die je hebt aangemaakt en gevuld met
-- de aangeleverde data (zie de opdracht op Canvas).
--
-- Voer het voorbeeld uit wat in de les behandeld is:
-- 1. Voer het volgende EXPLAIN statement uit:
   EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
   
   
--    "Gather  (cost=1000.00..6151.97 rows=1007 width=96)"
-- "  Workers Planned: 2"
-- "  ->  Parallel Seq Scan on order_lines  (cost=0.00..5051.27 rows=420 width=96)"
-- "        Filter: (stock_item_id = 9)"
   
--    Bekijk of je het resultaat begrijpt. Kopieer het explain plan onderaan de opdracht
-- 2. Voeg een index op stock_item_id toe:
   CREATE INDEX ord_lines_si_id_idx ON order_lines (stock_item_id);
-- 3. Analyseer opnieuw met EXPLAIN hoe de query nu uitgevoerd wordt
--    Kopieer het explain plan onderaan de opdracht
 EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
-- 4. Verklaar de verschillen. Schrijf deze hieronder op.
-- "Bitmap Heap Scan on order_lines  (cost=12.10..2302.12 rows=1007 width=96)"
-- "  Recheck Cond: (stock_item_id = 9)"
-- "  ->  Bitmap Index Scan on ord_lines_si_id_idx  (cost=0.00..11.85 rows=1007 width=0)"
-- "        Index Cond: (stock_item_id = 9)"

--bij de 2e keer gaat het via balanced tree search omdat er een index is
--veel sneller naar de goede rij inplaats alles langs te gaan - tot dat het een matchts is


-- S7.2.
--
-- 1. Maak de volgende twee query’s:
-- 	  A. Toon uit de order tabel de order met order_id = 73590
-- 	  B. Toon uit de order tabel de order met customer_id = 1028
SELECT * FROM orders WHERE order_id = 73590;
SELECT * FROM orders WHERE customer_id = 1028;
-- 2. Analyseer met EXPLAIN hoe de query’s uitgevoerd worden en kopieer het explain plan onderaan de opdracht
EXPLAIN SELECT * FROM orders WHERE order_id = 73590;
EXPLAIN SELECT * FROM orders WHERE customer_id = 1028;
-- "Index Scan using pk_sales_orders on orders  (cost=0.29..8.31 rows=1 width=155)"
-- "  Index Cond: (order_id = 73590)"

-- "Seq Scan on orders  (cost=0.00..1819.94 rows=107 width=155)"
-- "  Filter: (customer_id = 1028)"

-- 3. Verklaar de verschillen en schrijf deze op
--De eerste query gebruik index scan want order_id is de primary key dus dat gaat veel sneller, bij de 2e moet hij alles langs gaan

-- 4. Voeg een index toe, waarmee query B versneld kan worden
CREATE INDEX orders_customer_id_index ON orders (customer_id);
-- 5. Analyseer met EXPLAIN en kopieer het explain plan onder de opdracht
EXPLAIN SELECT * FROM orders WHERE customer_id = 1028;

-- "Bitmap Heap Scan on orders  (cost=5.12..308.96 rows=107 width=155)"
-- "  Recheck Cond: (customer_id = 1028)"
-- "  ->  Bitmap Index Scan on orders_customer_id_index  (cost=0.00..5.10 rows=107 width=0)"
-- "        Index Cond: (customer_id = 1028)"

-- 6. Verklaar de verschillen en schrijf hieronder op
-- Door de index kan er nu via de balanced tree search sneller naar de  costumer_id gezocht worden 

-- S7.3.A
--
-- Het blijkt dat customers regelmatig klagen over trage bezorging van hun bestelling.
-- Het idee is dat verkopers misschien te lang wachten met het invoeren van de bestelling in het systeem.
-- Daar willen we meer inzicht in krijgen.
-- We willen alle orders (order_id, order_date, salesperson_person_id (als verkoper),
--    het verschil tussen expected_delivery_date en order_date (als levertijd),  
--    en de bestelde hoeveelheid van een product zien (quantity uit order_lines).
-- Dit willen we alleen zien voor een bestelde hoeveelheid van een product > 250
--   (we zijn nl. als eerste geïnteresseerd in grote aantallen want daar lijkt het vaker mis te gaan)
-- En verder willen we ons focussen op verkopers wiens bestellingen er gemiddeld langer over doen.
-- De meeste bestellingen kunnen binnen een dag bezorgd worden, sommige binnen 2-3 dagen.
-- Het hele bestelproces is er op gericht dat de gemiddelde bestelling binnen 1.45 dagen kan worden bezorgd.
-- We willen in onze query dan ook alleen de verkopers zien wiens gemiddelde levertijd 
--  (expected_delivery_date - order_date) over al zijn/haar bestellingen groter is dan 1.45 dagen.
-- Maak om dit te bereiken een subquery in je WHERE clause.
-- Sorteer het resultaat van de hele geheel op levertijd (desc) en verkoper.
-- 1. Maak hieronder deze query (als je het goed doet zouden er 377 rijen uit moeten komen, en het kan best even duren...)


EXPLAIN
Select orders.order_id,  orders.order_date,  orders.salesperson_person_id as  verkoper ,   
orders.expected_delivery_date -  orders.order_date as  levertijd, order_lines.quantity
FROM orders
JOIN order_lines ON order_lines.order_id = orders.order_id
WHERE order_lines.quantity > 250 AND (orders.expected_delivery_date -  orders.order_date) > 1.45
ORDER BY levertijd DESC


-- S7.3.B
--



-- 1. Vraag het EXPLAIN plan op van je query (kopieer hier, onder de opdracht)
"Gather Merge  (cost=7627.55..7661.15 rows=288 width=20)"
"  Workers Planned: 2"
"  ->  Sort  (cost=6627.52..6627.88 rows=144 width=20)"
"        Sort Key: ((orders.expected_delivery_date - orders.order_date)) DESC"
"        ->  Nested Loop  (cost=0.29..6622.36 rows=144 width=20)"
"              ->  Parallel Seq Scan on order_lines  (cost=0.00..5051.27 rows=431 width=8)"
"                    Filter: (quantity > 250)"
"              ->  Index Scan using pk_sales_orders on orders  (cost=0.29..3.64 rows=1 width=16)"
"                    Index Cond: (order_id = order_lines.order_id)"
"                    Filter: (((expected_delivery_date - order_date))::numeric > 1.45)"
-- 2. Kijk of je met 1 of meer indexen de query zou kunnen versnellen
--deze 2 gebruik je met where
CREATE INDEX index1_orders ON order_lines (quantity);
CREATE INDEX index2_orders ON orders (expected_delivery_date);
CREATE INDEX index3_orders ON orders (order_date);
-- 3. Maak de index(en) aan en run nogmaals het EXPLAIN plan (kopieer weer onder de opdracht) 

-- 4. Wat voor verschillen zie je? Verklaar hieronder.
"Sort  (cost=4744.60..4745.46 rows=345 width=20)" --cost is lager
"  Sort Key: ((orders.expected_delivery_date - orders.order_date)) DESC"
"  ->  Hash Join  (cost=2353.84..4730.05 rows=345 width=20)"
"        Hash Cond: (orders.order_id = order_lines.order_id)"
"        ->  Seq Scan on orders  (cost=0.00..2187.91 rows=24532 width=16)" -sneller
"              Filter: (((expected_delivery_date - order_date))::numeric > 1.45)"
"        ->  Hash  (cost=2340.91..2340.91 rows=1034 width=8)"
"              ->  Bitmap Heap Scan on order_lines  (cost=12.31..2340.91 rows=1034 width=8)"
"                    Recheck Cond: (quantity > 250)"
"                    ->  Bitmap Index Scan on index1_orders  (cost=0.00..12.05 rows=1034 width=0)" --sneller
"                          Index Cond: (quantity > 250)"


-- S7.3.C
--
-- Zou je de query ook heel anders kunnen schrijven om hem te versnellen?
CREATE OR REPLACE  VIEW faster AS
Select orders.order_id,  orders.order_date,  orders.salesperson_person_id as  verkoper ,   
orders.expected_delivery_date -  orders.order_date as  levertijd, order_lines.quantity
FROM orders
JOIN order_lines ON order_lines.order_id = orders.order_id
ORDER BY order_lines DESC


EXPLAIN
SELECT order_id,  order_date,  verkoper ,    levertijd, quantity FROM  faster
WHERE quantity > 250 AND levertijd > 1.45


-- ..........
"Subquery Scan on faster  (cost=4744.60..4748.91 rows=345 width=20)"
"  ->  Sort  (cost=4744.60..4745.46 rows=345 width=140)"
"        Sort Key: order_lines.* DESC"
"        ->  Hash Join  (cost=2353.84..4730.05 rows=345 width=140)"
"              Hash Cond: (orders.order_id = order_lines.order_id)"
"              ->  Seq Scan on orders  (cost=0.00..2187.91 rows=24532 width=16)"
"                    Filter: (((expected_delivery_date - order_date))::numeric > 1.45)"
"              ->  Hash  (cost=2340.91..2340.91 rows=1034 width=128)"
"                    ->  Bitmap Heap Scan on order_lines  (cost=12.31..2340.91 rows=1034 width=128)"
"                          Recheck Cond: (quantity > 250)"
"                          ->  Bitmap Index Scan on index1_orders  (cost=0.00..12.05 rows=1034 width=0)"
"                                Index Cond: (quantity > 250)"


