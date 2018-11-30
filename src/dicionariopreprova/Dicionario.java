/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicionariopreprova;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 *
 * @author ALUNO
 */
public class Dicionario {
    private ArrayList<String> nomes;
    private Random rand;
    private ArrayList<Pair> nomesETels;
    
    public Dicionario(){
        rand = new Random();
        nomes = new ArrayList<String>();
        nomesETels = new ArrayList<Pair>();
        
        int index = rand.nextInt(2);
        
        //Busca os nomes no txt e joga no arraylist
        if(index == 0)
            nomes = buscarNomes("masculinos.txt");
        else
            nomes = buscarNomes("femininos.txt");
    }
    
    public ArrayList<Pair> gerarNomes(int quantidade){
        //Gera os nomes e os telefones aleatoriamente e coloca-os n array
        for(int c = 0; c < quantidade; c++){
            nomesETels.add(new Pair(gerarNome(), gerarTelefone()));
        }
        
        return nomesETels;
    }
    
    private String gerarNome(){
        // Escolhe randomicamento a quantidade de nomes que tera no nome completo
        int qtdNomes = rand.nextInt(5);
        if(qtdNomes < 2)
            qtdNomes += 2;

        //Escolhendo os nomes aleatoriamente e criando o nome composto
        StringBuffer nomeComp = new StringBuffer();
        for(int i = 0; i < qtdNomes; i++){
            if(i != 0 && i != qtdNomes)
                nomeComp.append(" ");

            int index = rand.nextInt(nomes.size());
            nomeComp.append(nomes.get(index));
        }
        
        return nomeComp.toString();
    }
    
    private String gerarTelefone(){
        //Criando telefone aleatoriamente
        StringBuffer telefone = new StringBuffer();
        for(int i = 0; i < 14; i++){
            //Cria um numero randomico entre 0 e 9
            int num = rand.nextInt(10);
            
            //Coloca o numero no telefone dependend da posição
            switch(i){
                case 0: 
                    telefone.append("(");
                    break;
                case 3:
                    telefone.append(")");
                    break;
                case 9:
                    telefone.append("-");
                    break;
                default:
                    telefone.append(num);
            }
        }
        
        return telefone.toString();
    }
        
    private ArrayList<String> buscarNomes(String arquivo){
        //Le o arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String line;
            //Adiciona as linhas no array de nomes
            while ((line = br.readLine()) != null) {
                nomes.add(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dicionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dicionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nomes;
    }
    
    public boolean serializar(String nomArquivo){
        boolean retorno = false;
        
        try {
            //Pega o arquivo passado por parametro
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomArquivo + ".fibdic"));
            //Escreve o objeto no arquivo
            oos.writeObject(nomesETels);
            retorno = true;
            oos.flush();
            oos.close();            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dicionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dicionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public ArrayList<Pair> desserializar(String nomArquivo){
        boolean retorno = false;
        ArrayList<Pair> desserializedList = new ArrayList<Pair>();
        
        try {
            //Pega o arquivo passado por parametro
            FileInputStream fis = new FileInputStream(nomArquivo);
            //Pega o objeto do arquivo
            ObjectInputStream ois = new ObjectInputStream(fis);
            //Le o objeto do arquivo e faz a conversão para o ArrayList de Pairs
            desserializedList = (ArrayList<Pair>) ois.readObject();
            ois.close();            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dicionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dicionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dicionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return desserializedList;
    }
}
