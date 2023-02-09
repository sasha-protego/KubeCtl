package com.protegotrust.kubectl

import java.nio.charset.StandardCharsets
import java.util
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1ObjectMeta
import io.kubernetes.client.openapi.models.V1Secret
import io.kubernetes.client.util.ClientBuilder

object KubeSecretsPusher {
  def main(args: Array[String]): Unit = {
    val meta = new V1ObjectMeta()
    meta.setName("custom-secret")
    meta.setNamespace("default")
    val secret = new V1Secret()
    secret.setKind("Secret")
    secret.setType("Opaque")
    secret.putStringDataItem("v1", "secret")
    secret.setMetadata(meta)

    Configuration.setDefaultApiClient(ClientBuilder.defaultClient());
    val api = new CoreV1Api()
    // Create
    val r = api.createNamespacedSecret("default", secret, " true", null, null, null)
    println(r)
    // Read
    val r2 = api.readNamespacedSecret("custom-secret", "default", " true")
    println({
        val data: util.Map[String, Array[Byte]] = r2.getData
        val aesKey: Array[Byte] = data.get("v1")
        val str = new String(aesKey, StandardCharsets.UTF_8)
        str
    })
    // Update
    r2.putStringDataItem("v2", "secret2")
    api.replaceNamespacedSecret("custom-secret", "default", r2, " true", null, null, null)
  }
}
