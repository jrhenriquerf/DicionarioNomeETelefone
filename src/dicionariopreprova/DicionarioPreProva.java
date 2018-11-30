/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionariopreprova;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 *
 * @author ALUNO
 */
public class DicionarioPreProva {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random rand = new Random();        
        Scanner scan = new Scanner(System.in);
        Dicionario dic = new Dicionario();
        
        System.out.println("Deseja abrir um arquivo e pesquisar ou criar um arquivo novo?\n1 - Abrir arquivo\t0 - Criar novo");
        int opcao = scan.nextInt();
        
        switch(opcao){
            case 0:
                System.out.println("Digite a quantidade de nomes e telefones que deseja criar: ");
                int qtd = scan.nextInt();

                System.out.println("Eis a sua lista de nomes e telefones: ");
                System.out.println(dic.gerarNomes(qtd));

                System.out.println("\nDeseja serializar e gravar em um arquivo? \n1 - SIM\t0 - NÃO");
                int gravaBin = scan.nextInt();

                if(gravaBin == 1){
                    System.out.println("Digite o nome do arquivo que deseja salvar: ");
                    String nomArquivo = scan.next();
                    
                    //Serializar o arquivo e criar ele na maquina .fibdic
                    dic.serializar(nomArquivo);
                }
                break;
            case 1:
                System.out.println("Digite o nome do arquivo .fibdic que deseja desserializar: ");
                String nomArquivo = scan.next();
                
                //Verificar se contem o .fibdic para colcoar se não tiver
                if(!nomArquivo.contains(".fibdic"))
                    nomArquivo += ".fibdic";
                
                //Desserializa o arquivo e cria um arrayList com os valores
                ArrayList<Pair> desserializedList = dic.desserializar(nomArquivo);
                
                if(desserializedList.size() > 0){
                    //Se houver valores
                    System.out.println("Valores encontrados, digite a pesquisa por nome ou telefone: ");
                    scan.nextLine();
                    String pesquisa = scan.nextLine();
                    boolean valorEncontrado = false;
                    String nome = "", telefone = "";
                    
                    //Procura o texto pesquisado entre nome ou telefone
                    for(int i = 0; i < desserializedList.size(); i++){
                        if(desserializedList.get(i).getKey().equals(pesquisa)){
                            valorEncontrado = true;
                        }else if(desserializedList.get(i).getValue().equals(pesquisa)){
                            valorEncontrado = true;
                        }
                        
                        //Se encontrar pega o nome e o telefone
                        if(valorEncontrado){
                            nome = desserializedList.get(i).getKey().toString();
                            telefone = desserializedList.get(i).getValue().toString();
                            break;
                        }
                    }
                    
                    if(!valorEncontrado)
                        System.out.println("Registro não encontrado");
                    else{
                        //Printa o nome e o telefone
                        System.out.println("Nome: " + nome);
                        System.out.println("Telefone: " + telefone);
                    }
                }else
                    System.out.println("Não foi retornado nenhum valor do arquivo solicitado!");
                
                break;
        }
    }    
}
