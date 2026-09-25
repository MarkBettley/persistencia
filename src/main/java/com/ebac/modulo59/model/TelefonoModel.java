package com.ebac.modulo59.model;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.Objects;
import java.util.Optional;

public class TelefonoModel {

    private final MongoCollection<Document> collection;

    public TelefonoModel(MongoDatabase database) {
        collection = database.getCollection("telefonos");
    }

    public void guardar(Document document) {
        collection.insertOne(document);
    }

    public void obtener() {
        FindIterable<Document> telefonos = collection.find();

        for (Document telefono : telefonos) {
            System.out.println(telefono);
        }
    }

    public Optional<Document> obtenerPorId(Document document) {
        Document telefono = collection.find(document).first();

        if (!Objects.isNull(telefono)) {
            System.out.println(telefono);
            return Optional.of(telefono);
        }

        return Optional.empty();
    }

    public void actualizar(Document documentoActual, Document documentoNuevo) {
        UpdateResult updateResult =
                collection.updateOne(documentoActual, documentoNuevo);

        if (updateResult.getModifiedCount() > 0) {
            System.out.println("Telefono actualizado con exito.");
        } else {
            System.out.println("El telefono no fue encontrado.");
        }
    }

    public void eliminar(Document document) {
        DeleteResult deleteResult = collection.deleteOne(document);

        if (deleteResult.getDeletedCount() > 0) {
            System.out.println("Telefono eliminado con exito.");
        } else {
            System.out.println("El telefono no fue encontrado.");
        }
    }
}
