/**
 * @Name: UBOAPIConstants.java
 * @Class_Description: This class holds the constants in the UBOAPI application
 *
 * @author: Cognizant Technology Solutions
 * @Created_On: Aug 29, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */

package com.dnb.ubo.v3.constants;

public class Query {

    public static final String BENEF_LIST_QUERY_1 = prepareBenefListQuery();

    public static final String CORPORATE_LINKAGE_QUERY_1 = "CYPHER 3.1 MATCH p=(O1:Organisation{duns_nbr:{queryDunsNumber}})-[r]->(O2:Organisation)";
    public static final String CORPORATE_LINKAGE_QUERY_2 = " WHERE (NOT EXISTS(O1.del_indc) or O1.del_indc=false) and (NOT EXISTS(O2.del_indc) or O2.del_indc=false)  and type(r) <> 'SHARES_HELD_BY'   RETURN type(r),O2.duns_nbr,O2.nme ";

    private Query() {
    }

    /**
     * Prepare benef list query.
     *
     * @return the string
     */
    private static String prepareBenefListQuery() {
        StringBuilder lPrepareBenefListQuery = new StringBuilder("CYPHER 3.1 MATCH (target:Organisation  {duns_nbr:{queryDunsNumber}}) WHERE NOT EXISTS(target.del_indc) ")
        .append("CALL apoc.path.subgraphNodes(target, {relationshipFilter:'SHARES_HELD_BY>'}) ")
        .append("YIELD node WITH node, target WHERE NOT EXISTS(node.del_indc) ")
        .append("WITH COLLECT(node) AS activeSubgraphNodes, target ")
        .append("UNWIND activeSubgraphNodes as shareHolder ")
        .append("MATCH (org)-[r:SHARES_HELD_BY*0..1]->(shareHolder) WHERE org IN activeSubgraphNodes ")
        .append("OPTIONAL MATCH (shareHolder)-[hmo:HAS_MAJORITY_OWNER]->(shOwn) WHERE NOT EXISTS(shOwn.del_indc) AND NOT (shareHolder)-[:SHARES_HELD_BY]->() ")
        .append("RETURN collect(DISTINCT id(startNode(hmo))) AS hmoNodes, ")
        .append("id(target) as targetDUNS,collect(DISTINCT id(shareHolder)) as nodes, ")
        .append("[r in collect(distinct last(r)) | [ id(startNode(r))+ '|' +substring(labels(startNode(r))[0],0,1),id(endNode(r))+ '|' +substring(labels(endNode(r))[0],0,1), r.owrp_pctg,id(r)]] as rels, ")
        .append("activeSubgraphNodes as firmographics");
        return lPrepareBenefListQuery.toString();
    }
}
