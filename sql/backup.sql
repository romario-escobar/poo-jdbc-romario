--
-- PostgreSQL database dump
--

-- Dumped from database version 18.6
-- Dumped by pg_dump version 18.6

-- Started on 2026-09-30 20:43:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 222 (class 1259 OID 16526)
-- Name: filme; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.filme (
    id integer NOT NULL,
    titulo_original character varying(150) NOT NULL,
    titulo_traduzido character varying(150),
    duracao_minutos integer NOT NULL,
    ano_lancamento integer NOT NULL,
    genero_id integer NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 16525)
-- Name: filme_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.filme_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4923 (class 0 OID 0)
-- Dependencies: 221
-- Name: filme_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.filme_id_seq OWNED BY public.filme.id;


--
-- TOC entry 220 (class 1259 OID 16516)
-- Name: genero_filme; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.genero_filme (
    id integer NOT NULL,
    descricao character varying(80) NOT NULL,
    classificacao_indicativa character varying(10) NOT NULL
);


--
-- TOC entry 219 (class 1259 OID 16515)
-- Name: genero_filme_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.genero_filme_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 219
-- Name: genero_filme_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.genero_filme_id_seq OWNED BY public.genero_filme.id;


--
-- TOC entry 4761 (class 2604 OID 16529)
-- Name: filme id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.filme ALTER COLUMN id SET DEFAULT nextval('public.filme_id_seq'::regclass);


--
-- TOC entry 4760 (class 2604 OID 16519)
-- Name: genero_filme id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.genero_filme ALTER COLUMN id SET DEFAULT nextval('public.genero_filme_id_seq'::regclass);


--
-- TOC entry 4917 (class 0 OID 16526)
-- Dependencies: 222
-- Data for Name: filme; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.filme VALUES (1, 'Interstellar', 'Interestelar', 169, 2014, 1);
INSERT INTO public.filme VALUES (2, 'The Matrix', 'Matrix', 136, 1999, 1);
INSERT INTO public.filme VALUES (3, 'Inception', 'A Origem', 148, 2010, 1);
INSERT INTO public.filme VALUES (4, 'Toy Story', 'Toy Story', 81, 1995, 2);
INSERT INTO public.filme VALUES (5, 'Spirited Away', 'A Viagem de Chihiro', 125, 2001, 2);
INSERT INTO public.filme VALUES (6, 'Coco', 'Viva: A Vida ‚ uma Festa', 105, 2017, 2);
INSERT INTO public.filme VALUES (7, 'The Dark Knight', 'Batman: O Cavaleiro das Trevas', 152, 2008, 3);
INSERT INTO public.filme VALUES (8, 'Mad Max: Fury Road', 'Mad Max: Estrada da F£ria', 120, 2015, 3);
INSERT INTO public.filme VALUES (9, 'The Shawshank Redemption', 'Um Sonho de Liberdade', 142, 1994, 4);
INSERT INTO public.filme VALUES (10, 'Parasite', 'Parasita', 132, 2019, 4);


--
-- TOC entry 4915 (class 0 OID 16516)
-- Dependencies: 220
-- Data for Name: genero_filme; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.genero_filme VALUES (1, 'Fic‡Æo Cient¡fica', '12');
INSERT INTO public.genero_filme VALUES (2, 'Anima‡Æo', 'L');
INSERT INTO public.genero_filme VALUES (3, 'A‡Æo', '14');
INSERT INTO public.genero_filme VALUES (4, 'Drama', '14');


--
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 221
-- Name: filme_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.filme_id_seq', 10, true);


--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 219
-- Name: genero_filme_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.genero_filme_id_seq', 4, true);


--
-- TOC entry 4765 (class 2606 OID 16536)
-- Name: filme filme_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.filme
    ADD CONSTRAINT filme_pkey PRIMARY KEY (id);


--
-- TOC entry 4763 (class 2606 OID 16524)
-- Name: genero_filme genero_filme_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.genero_filme
    ADD CONSTRAINT genero_filme_pkey PRIMARY KEY (id);


--
-- TOC entry 4766 (class 2606 OID 16537)
-- Name: filme filme_genero_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.filme
    ADD CONSTRAINT filme_genero_id_fkey FOREIGN KEY (genero_id) REFERENCES public.genero_filme(id) ON DELETE RESTRICT;


-- Completed on 2026-09-30 20:43:42

--
-- PostgreSQL database dump complete
--