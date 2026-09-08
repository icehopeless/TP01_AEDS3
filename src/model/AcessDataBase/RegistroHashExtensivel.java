/*
REGISTRO HASH EXTENSÍVEL

Esta interface apresenta os métodos que os objetos
a serem incluídos na tabela hash extensível devem 
conter.

Implementado pelo Prof. Marcos Kutova
v1.1 - 2021
*/
package model.AcessDataBase;

import java.io.IOException;

public interface RegistroHashExtensivel<T> {

  public int hashCode(); // chave numérica para ser usada no diretório

  public short size(); // tamanho FIXO do registro

  public byte[] serialize() throws IOException; // representação do elemento em um vetor de bytes

  public void deserialize(byte[] ba) throws IOException; // vetor de bytes a ser usado na construção do elemento

}