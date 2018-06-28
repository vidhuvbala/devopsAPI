package com.dnb.ubo.v4.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.exceptions.Neo4jException;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.util.Function;

import com.dnb.ubo.v4.constants.C;
import com.dnb.ubo.v4.exception.ApplicationException;
import com.dnb.ubo.v4.response.JsonBuilder;
import com.dnb.ubo.v4.response.ResponseWrapper;

public class DaoImplProxy implements Dao {


    /**
     * Method to fetch the owner ship structure/List of Beneficiaries from
     * database
     *
     * @param dunsNumber
     * @param countryCode
     * @return
     * @throws ApiException
     */

    @Override
    public ResponseWrapper getBeneficiaryOps(final Map<String, Object> reqDetails, final String operationFlag) throws ApplicationException {

        try {
            List<Node> nodeValueList = new ArrayList<>();
            Set<Node> nodeDetailsSet;
            Map<String, Object> dataResults = new HashMap<>();
            Set<Node> targetValueList = new HashSet<>();
            List<Map<String, Object>> linkageValueslist;

            targetValueList.add(getMockInternalNode());
            nodeValueList.add(getMockInternalNode());
            nodeValueList.add(getMockInternalBeneNode());
            nodeValueList.add(getMockInternalIndirNode());
            nodeValueList.add(getMockInternalIndirOneNode());
            nodeDetailsSet = new HashSet<>(targetValueList);
            nodeDetailsSet.addAll(nodeValueList);
            linkageValueslist = corporateLinkageValue();
            List<Object> relation = getMockInternalRelation();
            dataResults.put(C.TARGET_DUNS, 34071241L);
            dataResults.put("nodes", nodeValueList);
            dataResults.put("rels", relation);
            dataResults.put("details", nodeValueList);
            // Calculations and response creation for Beneficial ownership
            // structure
            if (C.BENEF_STRUCTURE.equals(operationFlag)) {

                return JsonBuilder.getBeneficialOwnershipStructureResponse(reqDetails, dataResults, linkageValueslist);
            }
            // Calculations and response creation for Beneficial List
            else {
                return JsonBuilder.getBeneficialOwnershipListResponse(reqDetails, dataResults, linkageValueslist);
            }
        } catch (IllegalArgumentException e) {
            throw new ApplicationException(C.UB019, e, "");
        } catch (Neo4jException e) {
            throw new ApplicationException(C.UB016, e, "");
        } catch (Exception ex) {
            throw new ApplicationException(C.UB019, ex, "");
        } finally {

        }
    }

    /**
     *
     * @return
     * @throws ApiException
     */
    public List<Map<String, Object>> corporateLinkageValue() throws ApplicationException {
        List<Map<String, Object>> linkageValues = new ArrayList<>();
        Map<String, Object> linkage = new HashMap<>();
        Map<String, Object> linkageSecond = new HashMap<>();
        Map<String, Object> linkageThird = new HashMap<>();

        linkage.put("O1.duns_nbr", "434503766");
        linkage.put("O1.nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
        linkage.put("type(r)", "PARENT_OF");
        linkage.put("O2.duns_nbr", "282223671");
        linkage.put("O2.nme", "GHANTY ROYAL");

        linkageSecond.put("O1.duns_nbr", "434503766");
        linkageSecond.put("O1.nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
        linkageSecond.put("type(r)", "ULTIMATE_PARENT_OF");
        linkageSecond.put("O2.duns_nbr", "282223671");
        linkageSecond.put("O2.nme", "GHANTY ROYAL");

        linkageThird.put("O1.duns_nbr", "434503766");
        linkageThird.put("O1.nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
        linkageThird.put("type(r)", "DOMESTIC_ULTIMATE_PARENT_OF");
        linkageThird.put("O2.duns_nbr", "282223671");
        linkageThird.put("O2.nme", "GHANTY ROYAL");

        linkageValues.add(linkage);
        linkageValues.add(linkageSecond);
        linkageValues.add(linkageThird);

        return linkageValues;
    }

    /**
     *
     * @return Node
     */
    public Node getMockNodeObject() {
        return new Node() {

            @Override
            public <T> Iterable<T> values(Function<Value, T> arg0) {
                return null;
            }

            @Override
            public Iterable<Value> values() {
                return null;
            }

            @Override
            public int size() {
                return 1;
            }

            @Override
            public Iterable<String> keys() {
                return null;
            }

            @Override
            public Value get(String arg0) {
                return null;
            }

            @Override
            public boolean containsKey(String arg0) {
                return false;
            }

            @Override
            public <T> Map<String, T> asMap(Function<Value, T> arg0) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                Map<String, Object> node = new HashMap<>();
                node.put("duns_nbr", "434503766");
                node.put("regn_nbr", "38154467500311");
                node.put("sic_cd", "5699");
                node.put("iso_2_char_ctry_code", "RE");
                node.put("ln_1_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("lgl_form", "451");
                node.put("upd_dt", "2016-10-09 22:37:28");
                node.put("terr", "Réunion");
                node.put("nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
                node.put("post_town", "ST DENIS");
                node.put(C.OUT_OF_BUSINESS, C.OUT_OF_BUSINESS_TRUE_CODE1);
                node.put("strt_dt", "1991");
                node.put("lcl_inds_cd_typ", "20078");
                node.put("brth_dt", "1990-02-07");
                node.put("naty", "AUS");
                node.put("pers_id", "12345678");
                node.put("post_cd", "743186");
                return node;
            }

            @Override
            public long id() {
                return 34071241;
            }

            @Override
            public Iterable<String> labels() {
                List<String> list = new ArrayList<String>();
                list.add("Organisation");

                Iterable<String> iterable = list;
                return iterable;
            }

            @Override
            public boolean hasLabel(String arg0) {
                return false;
            }
        };
    }

    public Node getMockNodeBenObject() {
        return new Node() {

            @Override
            public <T> Iterable<T> values(Function<Value, T> arg0) {
                return null;
            }

            @Override
            public Iterable<Value> values() {
                return null;
            }

            @Override
            public int size() {
                return 1;
            }

            @Override
            public Iterable<String> keys() {
                return null;
            }

            @Override
            public Value get(String arg0) {
                return null;
            }

            @Override
            public boolean containsKey(String arg0) {
                return false;
            }

            @Override
            public <T> Map<String, T> asMap(Function<Value, T> arg0) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                Map<String, Object> node = new HashMap<>();
                node.put("duns_nbr", "339962563");
                node.put("regn_nbr", "38154467500311");
                node.put("sic_cd", "5699");
                node.put("upd_dt", "2016-10-09 22:37:28");
                node.put("nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
                node.put(C.OUT_OF_BUSINESS, C.OUT_OF_BUSINESS_TRUE_CODE2);
                node.put("strt_dt", "1991");
                node.put("lcl_inds_cd_typ", "20078");
                node.put("cre_dt", "2016-10-09 22:37:28");
                node.put("brth_dt", "1990/02/07");
                node.put("naty", "AUS");
                node.put("pers_id", "12345678");
                node.put("post_cd", "743186");
                return node;
            }

            @Override
            public long id() {
                return 208780213;
            }

            @Override
            public Iterable<String> labels() {
                List<String> list = new ArrayList<String>();
                list.add(null);

                Iterable<String> iterable = list;
                return iterable;
            }

            @Override
            public boolean hasLabel(String arg0) {
                return false;
            }
        };
    }

    public Node getMockNodeIndirObject() {
        return new Node() {

            @Override
            public <T> Iterable<T> values(Function<Value, T> arg0) {
                return null;
            }

            @Override
            public Iterable<Value> values() {
                return null;
            }

            @Override
            public int size() {
                return 1;
            }

            @Override
            public Iterable<String> keys() {
                return null;
            }

            @Override
            public Value get(String arg0) {
                return null;
            }

            @Override
            public boolean containsKey(String arg0) {
                return false;
            }

            @Override
            public <T> Map<String, T> asMap(Function<Value, T> arg0) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                Map<String, Object> node = new HashMap<>();
                node.put("duns_nbr", "434245436");
                node.put("regn_nbr", "38154467500311");
                node.put("sic_cd", "5699");
                node.put("iso_2_char_ctry_code", "RE");
                node.put("ln_1_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("lgl_form", "451");
                node.put("upd_dt", "2016-10-09 22:37:28");
                node.put("terr", "Réunion");
                node.put("nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
                node.put("post_town", "ST DENIS");
                node.put("strt_dt", "1991");
                node.put("lcl_inds_cd_typ", "20078");
                node.put("cre_dt", "2016-10-09 22:37:28");
                node.put("ln_2_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("ln_3_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("post_cd", "80988");
                node.put("brth_dt", "1990/02/07");
                node.put("naty", "AUS");
                node.put("pers_id", "12345678");
                node.put("post_cd", "743186");
                node.put(C.OUT_OF_BUSINESS, C.OUT_OF_BUSINESS_TRUE_CODE3);
                node.put(C.OWNERSHIP_UNAVAILABLE_REASON_CODE, "30917");
                return node;
            }

            @Override
            public long id() {
                return 28777335;
            }

            @Override
            public Iterable<String> labels() {
                List<String> list = new ArrayList<String>();
                list.add("Organisation");

                Iterable<String> iterable = list;
                return iterable;
            }

            @Override
            public boolean hasLabel(String arg0) {
                return false;
            }
        };
    }

    public Node getMockNodeIndirOneObject() {
        return new Node() {

            @Override
            public <T> Iterable<T> values(Function<Value, T> arg0) {
                return null;
            }

            @Override
            public Iterable<Value> values() {
                return null;
            }

            @Override
            public int size() {
                return 1;
            }

            @Override
            public Iterable<String> keys() {
                return null;
            }

            @Override
            public Value get(String arg0) {
                return null;
            }

            @Override
            public boolean containsKey(String arg0) {
                return false;
            }

            @Override
            public <T> Map<String, T> asMap(Function<Value, T> arg0) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                Map<String, Object> node = new HashMap<>();
                node.put("duns_nbr", "434698347");
                node.put("regn_nbr", "38154467500311");
                node.put("sic_cd", "5699");
                node.put("iso_2_char_ctry_code", "RE");
                node.put("ln_1_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("lgl_form", "451");
                node.put("upd_dt", "2016-10-09 22:37:28");
                node.put("terr", "Réunion");
                node.put("nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
                node.put("post_town", "ST DENIS");
                node.put(C.OUT_OF_BUSINESS, C.OUT_OF_BUSINESS_TRUE_CODE3);
                node.put("strt_dt", "1991");
                node.put("lcl_inds_cd_typ", "20078");
                node.put("cre_dt", "2016-10-09 22:37:28");
                node.put("ln_2_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("ln_3_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("post_cd", "80988");
                node.put("brth_dt", "1990/02/07");
                node.put("naty", "AUS");
                node.put("pers_id", "12345678");
                node.put("post_cd", "743186");
                return node;
            }

            @Override
            public long id() {
                return 405360542;
            }

            @Override
            public Iterable<String> labels() {
                List<String> list = new ArrayList<String>();
                list.add("Individual");

                Iterable<String> iterable = list;
                return iterable;
            }

            @Override
            public boolean hasLabel(String arg0) {
                return false;
            }
        };
    }

    public InternalNode getMockInternalNode() {
        Node node = getMockNodeObject();
        List<String> finList = new ArrayList<String>();
        Map<String, Value> map = new HashMap<String, Value>();
        Iterable<String> cols = node.labels();
        for (String col : cols) {
            finList.add(col);
        }

        return new InternalNode(node.id(), finList, map) {

            @Override
            public Map<String, Object> asMap() {
                Map<String, Object> node = new HashMap<>();
                node.put("duns_nbr", "434503766");
                node.put("regn_nbr", "38154467500311");
                node.put("sic_cd", "5699");
                node.put("iso_2_char_ctry_code", "RE");
                node.put("ln_1_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("lgl_form", "451");
                node.put("upd_dt", "2016-10-09 22:37:28");
                node.put("terr", "Réunion");
                node.put("nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
                node.put("post_town", "ST DENIS");
                node.put(C.OUT_OF_BUSINESS, C.OUT_OF_BUSINESS_TRUE_CODE1);
                node.put("strt_dt", "1991");
                node.put("lcl_inds_cd_typ", "20078");
                node.put("cre_dt", "2016-10-09 22:37:28");
                node.put("ln_2_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("ln_3_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("post_cd", "80988");
                node.put("brth_dt", "1990/02/07");
                node.put("naty", "AUS");
                node.put("pers_id", "12345678");
                node.put("post_cd", "743186");
                node.put("ctrl_typ_cd", 9059);
                node.put("ctrl_typ_cfdc_cd", 13702);

                return node;
            }
        };
    }

    public InternalNode getMockInternalBeneNode() {
        Node node = getMockNodeBenObject();
        List<String> finList = new ArrayList<String>();
        Map<String, Value> map = new HashMap<String, Value>();
        Iterable<String> cols = node.labels();
        for (String col : cols) {
            finList.add(col);
        }

        return new InternalNode(node.id(), finList, map) {

            @Override
            public Map<String, Object> asMap() {
                Map<String, Object> node = new HashMap<>();
                node.put("duns_nbr", "339962563");
                node.put("regn_nbr", "38154467500311");
                node.put("sic_cd", "5699");
                node.put("upd_dt", "2016-10-09 22:37:28");
                node.put("nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
                node.put(C.OUT_OF_BUSINESS, C.OUT_OF_BUSINESS_TRUE_CODE2);
                node.put("strt_dt", "1991");
                node.put("lcl_inds_cd_typ", "20078");
                node.put("cre_dt", "2016-10-09 22:37:28");
                node.put("brth_dt", "1990-02-07");
                node.put("pers_id", "12345678");
                node.put("ctrl_typ_cd", 32495);
                node.put("ctrl_typ_cfdc_cd", 13698);
                return node;
            }
        };
    }

    public InternalNode getMockInternalIndirNode() {
        Node node = getMockNodeIndirObject();
        List<String> finList = new ArrayList<String>();
        Map<String, Value> map = new HashMap<String, Value>();
        Iterable<String> cols = node.labels();
        for (String col : cols) {
            finList.add(col);
        }

        return new InternalNode(node.id(), finList, map) {

            @Override
            public Map<String, Object> asMap() {
                Map<String, Object> node = new HashMap<>();
                node.put("duns_nbr", "434245436");
                node.put("regn_nbr", "38154467500311");
                node.put("sic_cd", "5699");
                node.put("iso_2_char_ctry_code", "RE");
                node.put("ln_1_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("lgl_form", "451");
                node.put("upd_dt", "2016-10-09 22:37:28");
                node.put("terr", "Réunion");
                node.put("nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
                node.put("post_town", "ST DENIS");
                node.put(C.OUT_OF_BUSINESS, C.OUT_OF_BUSINESS_TRUE_CODE3);
                node.put("strt_dt", "1991");
                node.put("lcl_inds_cd_typ", "20078");
                node.put("cre_dt", "2016-10-09 22:37:28");
                node.put("ln_2_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("ln_3_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("post_cd", "80988");
                node.put("brth_dt", "1990/02/07");
                node.put("naty", "AUS");
                node.put("pers_id", "12345678");
                node.put("post_cd", "743186");
                node.put(C.OWNERSHIP_UNAVAILABLE_REASON_CODE, "30917");
                node.put("ctrl_typ_cd", 9057);
                node.put("ctrl_typ_cfdc_cd", 13693);
                return node;
            }
        };
    }

    public InternalNode getMockInternalIndirOneNode() {
        Node node = getMockNodeIndirOneObject();
        List<String> finList = new ArrayList<String>();
        Map<String, Value> map = new HashMap<String, Value>();
        Iterable<String> cols = node.labels();
        for (String col : cols) {
            finList.add(col);
        }

        return new InternalNode(node.id(), finList, map) {

            @Override
            public Map<String, Object> asMap() {
                Map<String, Object> node = new HashMap<>();
                node.put("duns_nbr", "434698347");
                node.put("regn_nbr", "38154467500311");
                node.put("sic_cd", "5699");
                node.put("iso_2_char_ctry_code", "RE");
                node.put("ln_1_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("lgl_form", "451");
                node.put("upd_dt", "2016-10-09 22:37:28");
                node.put("terr", "Réunion");
                node.put("nme", "SOCIETE NOUVELLE DE GESTION DES MAGASINS GHANTY ROYAL");
                node.put("post_town", "ST DENIS");
                node.put("strt_dt", "1991");
                node.put("lcl_inds_cd_typ", "20078");
                node.put("cre_dt", "2016-10-09 22:37:28");
                node.put("ln_2_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("ln_3_adr", "40 RUE DU MARECHAL LECLERC");
                node.put("post_cd", "80988");
                node.put("brth_dt", "1990/02/07");
                node.put("naty", "AUS");
                node.put("pers_id", "12345678");
                node.put(C.OUT_OF_BUSINESS, C.OUT_OF_BUSINESS_TRUE_CODE3);
                node.put("post_cd", "743186");
                node.put(C.COUNTY, "PADOVA");
                node.put("ctrl_typ_cd", 9058);
                node.put("ctrl_typ_cfdc_cd", 13693);
                return node;
            }
        };
    }

    /**
     * Mock Relation Data
     *
     * @return List
     */
    public List<Object> getMockInternalRelation() {

        List<Object> relationList = new ArrayList<>();
        List<Object> relsFirst = new ArrayList<>();
        List<Object> relsSecond = new ArrayList<>();
        List<Object> relsThird = new ArrayList<>();
        List<Object> relsFourth = new ArrayList<>();
        List<Object> relsFifth = new ArrayList<>();

        relsFirst.add(C.ZERO, "34071241|O");
        relsFirst.add(C.ONE, "28777335|O");
        relsFirst.add(C.TWO, 75.0d);
        relsFirst.add(C.THREE, 226040159L);
        relsFirst.add(C.FOUR, "S");

        relsSecond.add(C.ZERO, "28777335|O");
        relsSecond.add(C.ONE, "26148368|O");
        relsSecond.add(C.TWO, 0.0d);
        relsSecond.add(C.THREE, 226066092L);
        relsSecond.add(C.FOUR, "S");

        relsThird.add(C.ZERO, "26148368|O");
        relsThird.add(C.ONE, "405360535|I");
        relsThird.add(C.TWO, 45.0d);
        relsThird.add(C.THREE, 195290492L);
        relsThird.add(C.FOUR, "H");

        relsFourth.add(C.ZERO, "26148368|O");
        relsFourth.add(C.ONE, "405360542|I");
        relsFourth.add(C.TWO, 45.0d);
        relsFourth.add(C.THREE, 195290504L);
        relsFourth.add(C.FOUR, "S");

        relsFifth.add(C.ZERO, "26148368|O");
        relsFifth.add(C.ONE, "405360554|I");
        relsFifth.add(C.TWO, 10.0d);
        relsFifth.add(C.THREE, 195290519L);
        relsFifth.add(C.FOUR, "S");

        relationList.add(relsFirst);
        relationList.add(relsSecond);
        relationList.add(relsThird);
        relationList.add(relsFourth);
        relationList.add(relsFifth);

        return relationList;

    }

}
