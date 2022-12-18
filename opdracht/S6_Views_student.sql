-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S6: Views
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------


-- S6.1.
--
-- 1. Maak een view met de naam "deelnemers" waarmee je de volgende gegevens uit de tabellen inschrijvingen en uitvoering combineert:
--    inschrijvingen.cursist, inschrijvingen.cursus, inschrijvingen.begindatum, uitvoeringen.docent, uitvoeringen.locatie

CREATE OR REPLACE  VIEW deelnemers AS
	SELECT inschrijvingen.cursist, inschrijvingen.cursus, inschrijvingen.begindatum, uitvoeringen.docent, uitvoeringen.locatie
	FROM inschrijvingen 
	JOIN uitvoeringen  on uitvoeringen.cursus = inschrijvingen.cursus and uitvoeringen.begindatum = inschrijvingen.begindatum;


-- 2. Gebruik de view in een query waarbij je de "deelnemers" view combineert met de "personeels" view (behandeld in de les):
    CREATE OR REPLACE VIEW personeel AS
	     SELECT mnr, voorl, naam as medewerker, afd, functie
      FROM medewerkers;

SELECT * FROM deelnemers 
JOIN personeel ON personeel.mnr = deelnemers.cursist
WHERE deelnemers.locatie = 'DE MEERN' and personeel.functie  = 'MANAGER';
	  
	  
-- 3. Is de view "deelnemers" updatable ? Waarom ?
-- nee want heeft joins dus heeft afhnakelijk van meerdere tabellen



-- S6.2.
--
-- 1. Maak een view met de naam "dagcursussen". Deze view dient de gegevens op te halen: 
--      code, omschrijving en type uit de tabel curssussen met als voorwaarde dat de lengte = 1. Toon aan dat de view werkt. 
CREATE OR REPLACE  VIEW dagcursussen AS
	SELECT code, omschrijving, type
	FROM cursussen
	WHERE lengte = 1;


SELECT lengte FROM dagcursussen 
JOIn cursussen ON cursussen.code = dagcursussen.code;

-- 2. Maak een tweede view met de naam "daguitvoeringen". 
--    Deze view dient de uitvoeringsgegevens op te halen voor de "dagcurssussen" (gebruik ook de view "dagcursussen"). Toon aan dat de view werkt
CREATE OR REPLACE  VIEW daguitvoeringen AS
	SELECT *
	FROM dagcursussen;
	
SELECT * FROM daguitvoeringen;

--data vergelijken:

SELECT 
(SELECT Count(*) FROM daguitvoeringen) as count_uitvoering , 
(SELECT Count(*) FROM dagcursussen) as count_cursus,
(SELECT min(omschrijving) FROM daguitvoeringen) as min_uitvoering , 
(SELECT min(omschrijving) FROM dagcursussen) as min_cursus,
(SELECT max(omschrijving) FROM daguitvoeringen) as max_uitvoering , 
(SELECT max(omschrijving) FROM dagcursussen) as max_cursus;

-- 3. Verwijder de views en laat zien wat de verschillen zijn bij DROP view <viewnaam> CASCADE en bij DROP view <viewnaam> RESTRICT

-- Als ik dagcursussen wil dan mag dat niet want dag uitvoeringen is daar afhankelijk van
--DROP VIEW dagcursussen RESTRICT;
SELECT * FROM dagcursussen ;

DROP VIEW dagcursussen CASCADE;

-- geen afhankelijk heden 
--DROP VIEW deelnemers RESTRICT;
DROP VIEW personeel RESTRICT;

--DROP VIEW deelnemers CASCADE;
--DROP VIEW personeel CASCADE;


