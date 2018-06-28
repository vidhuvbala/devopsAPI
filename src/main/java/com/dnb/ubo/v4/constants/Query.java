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

package com.dnb.ubo.v4.constants;

public class Query {

    public static final String BENEF_LIST_QUERY_1 = "CYPHER 3.1 MATCH (target:Organisation  {duns_nbr:{queryDunsNumber}}) WHERE NOT EXISTS(target.del_indc)  CALL apoc.path.subgraphNodes(target, {relationshipFilter:'SHARES_HELD_BY>|HAS_MAJORITY_OWNER>', maxLevel:{depth}}) YIELD node WITH target, node WHERE NOT EXISTS(node.del_indc) WITH target, COLLECT(node) as activeSubgraphNodes UNWIND activeSubgraphNodes as shareHolder"
                                                   +" MATCH (org)-[r:SHARES_HELD_BY|HAS_MAJORITY_OWNER*0..1]->(shareHolder) WHERE org in activeSubgraphNodes RETURN activeSubgraphNodes as firmographics , id(target) as targetDUNS,collect(DISTINCT id(shareHolder)) as nodes,[r in collect(distinct last(r)) | [ id(startNode(r))+ '|' +substring(labels(startNode(r))[0],0,1),id(endNode(r))+ '|' +substring(labels(endNode(r))[0],0,1), r.owrp_pctg,id(r), substring(type(r),0,1)]] as rels";

    public static final String CORPORATE_LINKAGE_QUERY_1 = "CYPHER 3.1 MATCH p=(O1:Organisation {duns_nbr:{queryDunsNumber}})-[r:HAS_PARENT|HAS_DOMESTIC_ULTIMATE_PARENT|HAS_ULTIMATE_PARENT]->(O2:Organisation) WHERE (NOT EXISTS(O1.del_indc) or O1.del_indc=false) and (NOT EXISTS(O2.del_indc) or O2.del_indc=false)   RETURN type(r),O2.duns_nbr,O2.nme";

    private Query() {
    }
}
