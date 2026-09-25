package com.ebac.modulo59;

import com.ebac.modulo59.dto.Direccion;
import com.ebac.modulo59.model.DireccionModel;
import com.ebac.modulo59.model.TelefonoModel;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Optional;

public class Contexto {

    public static void main(String[] args) {

        // -------------------------
        // JPA / HIBERNATE - MYSQL
        // -------------------------

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("coneccionLocalMySQL");

        EntityManager entityManager =
                entityManagerFactory.createEntityManager();

        DireccionModel direccionModel =
                new DireccionModel(entityManager);

        // Crear direccion
        Direccion nuevaDireccion = new Direccion();
        nuevaDireccion.setIdUsuario(1);
        nuevaDireccion.setCalle("Avenida Universidad");
        nuevaDireccion.setNumero(100);
        nuevaDireccion.setEstado("Hidalgo");
        direccionModel.guardar(nuevaDireccion);

        // Obtener direccion
        Direccion direccion =
                direccionModel.obtenerPorId(nuevaDireccion.getIdDireccion());

        System.out.println("Direccion creada: " + direccion);

        // Actualizar direccion
        direccion.setNumero(200);
        direccionModel.actualizar(direccion);

        System.out.println("Direccion actualizada: "
                + direccionModel.obtenerPorId(direccion.getIdDireccion()));

        // Listar direcciones
        direccionModel.obtenerDirecciones()
                .forEach(System.out::println);

        // Eliminar direccion
        direccionModel.eliminar(direccion);

        entityManager.close();
        entityManagerFactory.close();


        // -------------------------
        // MONGODB
        // -------------------------

        String connectionString = "mongodb://localhost:27017";

        MongoClient mongoClient =
                MongoClients.create(connectionString);

        MongoDatabase database =
                mongoClient.getDatabase("modulo60");

        TelefonoModel telefonoModel =
                new TelefonoModel(database);

        // Crear telefono
        Document telefono = new Document("idUsuario", 2)
                .append("numero", "+52 771 987 6543")
                .append("tipo", "celular");

        telefonoModel.guardar(telefono);

        // Listar telefonos
        System.out.println("Telefonos:");
        telefonoModel.obtener();

        // Obtener telefono por id
        ObjectId objectId = telefono.getObjectId("_id");
        Document documentoABuscar =
                new Document("_id", objectId);

        Optional<Document> telefonoEncontrado =
                telefonoModel.obtenerPorId(documentoABuscar);

        // Actualizar telefono
        telefonoEncontrado.ifPresent(telefonoActual -> {
            Document nuevosDatos =
                    new Document("tipo", "oficina");

            Document telefonoActualizado =
                    new Document("$set", nuevosDatos);

            telefonoModel.actualizar(
                    telefonoActual,
                    telefonoActualizado
            );
        });

        // Listar despues de actualizar
        telefonoModel.obtener();

        // Eliminar telefono
Optional<Document> telefonoActualizado =
        telefonoModel.obtenerPorId(documentoABuscar);

telefonoActualizado.ifPresent(telefonoModel::eliminar);

// Listar despues de eliminar
telefonoModel.obtener();

        mongoClient.close();
    }
}
