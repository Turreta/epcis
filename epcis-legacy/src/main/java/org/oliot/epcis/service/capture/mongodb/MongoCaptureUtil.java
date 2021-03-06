package org.oliot.epcis.service.capture.mongodb;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.json.JSONObject;
import org.oliot.epcis.configuration.Configuration;
import org.oliot.epcis.serde.mongodb.AggregationEventWriteConverter;
import org.oliot.epcis.serde.mongodb.MasterDataWriteConverter;
import org.oliot.epcis.serde.mongodb.ObjectEventWriteConverter;
import org.oliot.epcis.serde.mongodb.QuantityEventWriteConverter;
import org.oliot.epcis.serde.mongodb.TransactionEventWriteConverter;
import org.oliot.epcis.serde.mongodb.TransformationEventWriteConverter;
import org.oliot.epcis.service.subscription.TriggerEngine;
import org.oliot.model.epcis.AggregationEventType;
import org.oliot.model.epcis.ObjectEventType;
import org.oliot.model.epcis.QuantityEventType;
import org.oliot.model.epcis.TransactionEventType;
import org.oliot.model.epcis.TransformationEventType;
import org.oliot.model.epcis.VocabularyType;

import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;

/**
 * Copyright (C) 2014 Jaewook Byun
 *
 * This project is part of Oliot (oliot.org), pursuing the implementation of
 * Electronic Product Code Information Service(EPCIS) v1.1 specification in
 * EPCglobal.
 * [http://www.gs1.org/gsmp/kc/epcglobal/epcis/epcis_1_1-standard-20140520.pdf]
 * 
 *
 * @author Jaewook Jack Byun, Ph.D student
 * 
 *         Korea Advanced Institute of Science and Technology (KAIST)
 * 
 *         Real-time Embedded System Laboratory(RESL)
 * 
 *         bjw0829@kaist.ac.kr, bjw0829@gmail.com
 */

public class MongoCaptureUtil {

	public void capture(AggregationEventType event, String userID, String accessModifier, Integer gcpLength) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("AggregationEvent",
				BsonDocument.class);

		// Utilize Aggregation Event Write Converter itself
		AggregationEventWriteConverter wc = new AggregationEventWriteConverter();
		BsonDocument object2Save = wc.convert(event, gcpLength);
		if (userID != null && accessModifier != null) {
			object2Save.put("userID", new BsonString(userID));
			object2Save.put("accessModifier", new BsonString(accessModifier));
		}
		if (Configuration.isTriggerSupported == true) {
			TriggerEngine.examineAndFire("AggregationEvent", object2Save);
		}
		collection.insertOne(object2Save);
		Configuration.logger.info(" Event Saved ");
	}

	public void capture(ObjectEventType event, String userID, String accessModifier, Integer gcpLength) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("ObjectEvent",
				BsonDocument.class);

		// Utilize Object Event Write Converter itself
		ObjectEventWriteConverter wc = new ObjectEventWriteConverter();
		BsonDocument object2Save = wc.convert(event, gcpLength);
		if (userID != null && accessModifier != null) {
			object2Save.put("userID", new BsonString(userID));
			object2Save.put("accessModifier", new BsonString(accessModifier));
		}
		if (Configuration.isTriggerSupported == true) {
			TriggerEngine.examineAndFire("ObjectEvent", object2Save);
		}
		collection.insertOne(object2Save);
		Configuration.logger.info(" Event Saved ");
	}

	public void capture(QuantityEventType event, String userID, String accessModifier, Integer gcpLength) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("QuantityEvent",
				BsonDocument.class);

		// Utilize Quantity Event Write Converter itself
		QuantityEventWriteConverter wc = new QuantityEventWriteConverter();
		BsonDocument object2Save = wc.convert(event, gcpLength);
		if (userID != null && accessModifier != null) {
			object2Save.put("userID", new BsonString(userID));
			object2Save.put("accessModifier", new BsonString(accessModifier));
		}
		if (Configuration.isTriggerSupported == true) {
			TriggerEngine.examineAndFire("QuantityEvent", object2Save);
		}
		collection.insertOne(object2Save);
		Configuration.logger.info(" Event Saved ");
	}

	public void capture(TransactionEventType event, String userID, String accessModifier, Integer gcpLength) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("TransactionEvent",
				BsonDocument.class);

		// Utilize Transaction Event Write Converter itself
		TransactionEventWriteConverter wc = new TransactionEventWriteConverter();
		BsonDocument object2Save = wc.convert(event, gcpLength);
		if (userID != null && accessModifier != null) {
			object2Save.put("userID", new BsonString(userID));
			object2Save.put("accessModifier", new BsonString(accessModifier));
		}
		if (Configuration.isTriggerSupported == true) {
			TriggerEngine.examineAndFire("TransactionEvent", object2Save);
		}
		collection.insertOne(object2Save);
		Configuration.logger.info(" Event Saved ");
	}

	public void capture(TransformationEventType event, String userID, String accessModifier, Integer gcpLength) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("TransformationEvent",
				BsonDocument.class);

		// Utilize Transaction Event Write Converter itself
		TransformationEventWriteConverter wc = new TransformationEventWriteConverter();
		BsonDocument object2Save = wc.convert(event, gcpLength);
		if (userID != null && accessModifier != null) {
			object2Save.put("userID", new BsonString(userID));
			object2Save.put("accessModifier", new BsonString(accessModifier));
		}
		if (Configuration.isTriggerSupported == true) {
			TriggerEngine.examineAndFire("TransformationEvent", object2Save);
		}
		collection.insertOne(object2Save);
		Configuration.logger.info(" Event Saved ");
	}

	public void capture(VocabularyType vocabulary) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("MasterData",
				BsonDocument.class);

		MasterDataWriteConverter converter = new MasterDataWriteConverter();
		BsonDocument voc = converter.convert(vocabulary);

		collection.insertOne(voc);
		Configuration.logger.info(" Vocabulary Saved ");
	}

	// JsonObject event capture series..

	public void captureObjectEvent(JSONObject event) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("ObjectEvent",
				BsonDocument.class);
		BsonDocument dbObject = BsonDocument.parse(event.toString());
		if (Configuration.isTriggerSupported == true) {
			TriggerEngine.examineAndFire("ObjectEvent", dbObject);
		}
		collection.insertOne(dbObject);
		Configuration.logger.info(" Event Saved ");
	}

	public void captureAggregationEvent(JSONObject event) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("AggregationEvent",
				BsonDocument.class);

		BsonDocument dbObject = (BsonDocument) JSON.parse(event.toString());
		if (Configuration.isTriggerSupported == true) {
			TriggerEngine.examineAndFire("AggregationEvent", dbObject);
		}
		collection.insertOne(dbObject);
		Configuration.logger.info(" Event Saved ");
	}

	public void captureTransformationEvent(JSONObject event) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("TransformationEvent",
				BsonDocument.class);

		BsonDocument dbObject = (BsonDocument) JSON.parse(event.toString());
		if (Configuration.isTriggerSupported == true) {
			TriggerEngine.examineAndFire("TransformationEvent", dbObject);
		}
		collection.insertOne(dbObject);
		Configuration.logger.info(" Event Saved ");
	}

	public void captureTransactionEvent(JSONObject event) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("TransactionEvent",
				BsonDocument.class);

		BsonDocument dbObject = (BsonDocument) JSON.parse(event.toString());
		if (Configuration.isTriggerSupported == true) {
			TriggerEngine.examineAndFire("TransactionEvent", dbObject);
		}
		collection.insertOne(dbObject);
		Configuration.logger.info(" Event Saved ");
	}

	public void masterdata_capture(JSONObject event) {

		MongoCollection<BsonDocument> collection = Configuration.mongoDatabase.getCollection("MasterData",
				BsonDocument.class);

		BsonDocument dbObject = (BsonDocument) JSON.parse(event.toString());

		collection.insertOne(dbObject);
		Configuration.logger.info(" Event Saved ");
	}
}
