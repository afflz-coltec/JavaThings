/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetil;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import projetil.bulkhead.Bulkhead;
import projetil.area.Area;
import projetil.excecoes.ArgumentCountException;
import projetil.excecoes.InvalidBuckheadAngleException;
import projetil.excecoes.InvalidPositionException;
import projetil.geometricutils.Dot;
import projetil.projeteis.Projectile;
import projetil.projeteis.MediumProjectile;
import projetil.projeteis.FastProjectile;
import projetil.util.Entradas;
import projetil.util.Saidas;

/**
 * Main class.
 * @author Pedro
 */
public class MainProjetil {

    /**
     * Main method that calls the others.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Lista de projéteis.
        ArrayList<Projectile> ProjectilesList = new ArrayList<>();

        // Caderno da morte dos projéteis.
        ArrayList<Projectile> DeathNote = new ArrayList<>();

        // Lista de anteparos.
        ArrayList<Bulkhead> BulkheadList = new ArrayList<>();

        // Menu de opções: Projétil, Anteparo ou Criação Finalizada
        String[] Menu = {"Projectile", "Bulkhead", "Done"};

        // Opções de projéteis: Projétil Rápido, Médio ou Lento
        String[] ProjectilesOptions = {"Fast Projectile", "Medium Projectile", "Slow Projectile"};

        // Variável pra determinar se o usuário terminou de criar objetos.
        boolean doneCreating = false;

        // Variável pra determinar se o usuário já criou uma área.
        boolean AreaSettingsDone = false;

        // Inicialização de uma área para posterior criação dentro do loop.
        Area area = null;
        
        // 1a etapa
        // Iniciando o processo de criação de objetos...
        Saidas.dialogoInformacao("Projectiles", "Starting creation process...");

        // Enquanto o usuário quiser criar objetos, executa os seguintes comandos.
        while (doneCreating != true) {

            try {

                // Se o usuário ainda não criou uma área...
                if (!AreaSettingsDone) {

                    // Pede as dimensões...
                    String AParams = (Entradas.dialogoEntrada("Area Configuration", "height,width"));
                    
                    if ( AParams == null ) {
                        throw new ArgumentCountException();
                    }
                    
                    String[] AreaParams = AParams.split(",");

                    // Se a variável com os parâmetros não conter 2 argumentos, joga uma exceção ArgumentCountException.
                    if (AreaParams.length != 2) {
                        throw new ArgumentCountException();
                    }
                    // Se chegou até aqui, cria a área...
                    area = new Area(Integer.parseInt(AreaParams[0]), Integer.parseInt(AreaParams[1]));

                    // Informa a criação com sucesso.
                    Saidas.dialogoInformacao("Area Configuration", "Area setted! Width: " + area.getWidth() + "; Height: " + area.getHeight() + "\n");

                    // Área pronta, AreaSettingsDone = true.
                    AreaSettingsDone = true;

                }

                // Pergunta o usuário o que ele quer criar.
                String ObjType = Entradas.dialogoOpcoes("Object Creation", "What would you like to create?", Menu, Menu[0]);

                switch (ObjType) {

                    // Caso escolha projétil...
                    case "Projectile":

                        // Pergunta o usuário qual tipo de projétil ele quer criar.
                        String ProjectilesType = Entradas.dialogoOpcoes("Projectiles Creation", "Projectiles", ProjectilesOptions, ProjectilesOptions[0]);

                        switch (ProjectilesType) {

                            // Caso escolha o tipo Sniper...
                            case "Fast Projectile":
                                
                                String FParams = Entradas.dialogoEntrada("Projectile Settings", "InitX,InitY,Angle");
                                
                                if ( FParams == null ) {
                                    throw new ArgumentCountException();
                                }
                                
                                String[] FProjectileParams = FParams.split(",");

                                // Se a variável não conter 3 parâmetros, joga uma ArgumentCountException.
                                if ( FProjectileParams.length != 3 ) {
                                    throw new ArgumentCountException(); 
                                }
                                // E se as posições forem negativas, joga uma InvalidPositionException.
                                else if ( Integer.parseInt(FProjectileParams[0]) < 0 || 
                                          Integer.parseInt(FProjectileParams[1]) < 0 ) {
                                    throw new InvalidPositionException();
                                }
                                
                                // Variável temporária para checar algumas condições.
                                Projectile tmp1 = new FastProjectile(Integer.parseInt(FProjectileParams[0]),
                                                                     Integer.parseInt(FProjectileParams[1]),
                                                                     Integer.parseInt(FProjectileParams[2])
                                                                    );
                                
                                // Para cada vértice do novo projétil...
                                for ( Dot d : tmp1.getVList() ) {
                                    // Checa se o novo projétil está fora da área. Se sim, joga uma InvalidPositionException.
                                    if ( (d.getX() < 0 || d.getY() < 0) || (d.getX() > area.getWidth() || d.getY() > area.getHeight()) ) {
                                        throw new InvalidPositionException();
                                    }
                                }
                                
                                // Para cada projétil já criado...
                                for ( Projectile p : ProjectilesList ) {
                                    // Checa se o novo invade sua área. Se sim, joga uma InvalidPositionException.
                                    if ( tmp1.CheckColision(p) ) {
                                        throw new InvalidPositionException();
                                    }
                                }

                                // Se chegou até aqui, o projétil é válido e é adicionado à lista de projéteis.
                                ProjectilesList.add( new FastProjectile( Integer.parseInt(FProjectileParams[0]),
                                                                         Integer.parseInt(FProjectileParams[1]),
                                                                         Integer.parseInt(FProjectileParams[2])
                                                                        ) );
                                break;

                            // Caso escolha o tipo Revolver...
                            case "Medium Projectile":
                                
                                String MParams = Entradas.dialogoEntrada("Projectile Settings", "InitX,InitY,Angle");
                                
                                if ( MParams == null ) {
                                    throw new ArgumentCountException();
                                }
                                
                                String[] MProjectileParams = MParams.split(",");

                                // Se a variável não conter 3 parâmetros, joga uma ArgumentCountException.
                                if (MProjectileParams.length != 3) {
                                    throw new ArgumentCountException();
                                } 
                                // E se as posições forem negativas, joga uma InvalidPositionException.
                                else if ( Integer.parseInt(MProjectileParams[0]) < 0 || 
                                          Integer.parseInt(MProjectileParams[1]) < 0 ) {
                                    throw new InvalidPositionException();
                                }

                                // Variável temporária para checar algumas condições.
                                Projectile tmp2 = new MediumProjectile(Integer.parseInt(MProjectileParams[0]),
                                                                       Integer.parseInt(MProjectileParams[1]),
                                                                       Integer.parseInt(MProjectileParams[2])
                                                                      );
                                
                                // Para cada vértice do novo projétil...
                                for ( Dot d : tmp2.getVList() ) {
                                    // Checa se o novo projétil está fora da área. Se sim, joga uma InvalidPositionException.
                                    if ( (d.getX() < 0 || d.getY() < 0) || (d.getX() > area.getWidth() || d.getY() > area.getHeight()) ) {
                                        throw new InvalidPositionException();
                                    }
                                }
                                
                                // Para cada projétil já criado...
                                for ( Projectile p : ProjectilesList ) {
                                    // Checa se o novo invade sua área. Se sim, joga uma InvalidPositionException.
                                    if ( tmp2.CheckColision(p) ) {
                                        throw new InvalidPositionException();
                                    }
                                }

                                // Se chegou até aqui, o projétil é válido e é adicionado à lista de projéteis.
                                ProjectilesList.add( new MediumProjectile( Integer.parseInt(MProjectileParams[0]),
                                                                           Integer.parseInt(MProjectileParams[1]),
                                                                           Integer.parseInt(MProjectileParams[2])
                                                                         ) );
                                break;

                            // Caso escolha o tipo Slow Projectile...
                            case "Slow Projectile":
                                
                                String SParams = Entradas.dialogoEntrada("Projectile Settings", "InitX,InitY,Angle");
                                
                                if ( SParams == null ) {
                                    throw new ArgumentCountException();
                                }
                                
                                String[] SProjectileParams = SParams.split(",");

                                // Se a variável não conter 3 parâmetros, joga uma ArgumentCountException.
                                if (SProjectileParams.length != 3) {
                                    throw new ArgumentCountException();
                                } 
                                // E se as posições forem negativas, joga uma InvalidPositionException.
                                else if ( Integer.parseInt(SProjectileParams[0]) < 0 || 
                                          Integer.parseInt(SProjectileParams[1]) < 0 ) {
                                    throw new InvalidPositionException();
                                }

                                // Variável temporária para checar algumas condições.
                                Projectile tmp3 = new MediumProjectile(Integer.parseInt(SProjectileParams[0]),
                                                                       Integer.parseInt(SProjectileParams[1]),
                                                                       Integer.parseInt(SProjectileParams[2])
                                                                      );
                                
                                // Para cada vértice do novo projétil...
                                for ( Dot d : tmp3.getVList() ) {
                                    // Checa se o novo projétil está fora da área. Se sim, joga uma InvalidPositionException.
                                    if ( (d.getX() < 0 || d.getY() < 0) || (d.getX() > area.getWidth() || d.getY() > area.getHeight()) ) {
                                        throw new InvalidPositionException();
                                    }
                                }
                                
                                // Para cada projétil já criado...
                                for ( Projectile p : ProjectilesList ) {
                                    // Checa se o novo invade sua área. Se sim, joga uma InvalidPositionException.
                                    if ( tmp3.CheckColision(p) ) {
                                        throw new InvalidPositionException();
                                    }
                                }

                                // Se chegou até aqui, o projétil é válido e é adicionado à lista de projéteis.
                                ProjectilesList.add( new MediumProjectile( Integer.parseInt(SProjectileParams[0]),
                                                                           Integer.parseInt(SProjectileParams[1]),
                                                                           Integer.parseInt(SProjectileParams[2])
                                                                         ) );
                                
                                break;

                            // Se o usuário clicar no "x", apertar a Escape key ou clicar em cancelar, pergunta se ele quer criar mais objetos.
                            default:
                                if (Entradas.dialogoSimNao("Creating Process", "Would you like to create more objects?") == JOptionPane.NO_OPTION) {
                                    doneCreating = true;
                                }
                                break;

                        }

                        break;

                    // Caso o usuário escolha a criação de um anteparo...
                    case "Bulkhead": 

                        String[] BuckheadParams = (Entradas.dialogoEntrada("Buckhead Creation", "Height,Width,Angle,XPos,YPos")).split(",");

                        // Se a variável não conter 5 parâmetros, joga uma ArgumentCountException.
                        if (BuckheadParams.length != 5) {
                            throw new ArgumentCountException();
                        }
                        
                        // Variável temporária para checar algumas condições.
                        Bulkhead tmp4 = new Bulkhead(Integer.parseInt(BuckheadParams[0]),
                                                     Integer.parseInt(BuckheadParams[1]),
                                                     Integer.parseInt(BuckheadParams[2]),
                                                     Integer.parseInt(BuckheadParams[3]),
                                                     Integer.parseInt(BuckheadParams[4]));
                        
                        
                        // Para cada vértice do novo anteparo...
                        for ( Dot d : tmp4.getVList() ) {
                            // Checa se o novo anteparo está fora da área. Se sim, joga uma InvalidPositionException.
                            if ( (d.getX() < 0 || d.getY() < 0) || (d.getX() > area.getWidth() || d.getY() > area.getHeight()) ) {
                                throw new InvalidPositionException();
                            }
                        }
                        
                        // Para cada anteparo já criado...
                        for ( Bulkhead b : BulkheadList ) {
                            // Checa se o novo invade sua área. Se sim, joga uma InvalidPositionException.
                            if ( tmp4.CheckColision(b) ) {
                                throw new InvalidPositionException();
                            }
                            
                        }

                        // Se chegou até aqui, o anteparo é válido e é 
                        BulkheadList.add( new Bulkhead( Integer.parseInt(BuckheadParams[0]),
                                                        Integer.parseInt(BuckheadParams[1]),
                                                        Integer.parseInt(BuckheadParams[2]),
                                                        Integer.parseInt(BuckheadParams[3]),
                                                        Integer.parseInt(BuckheadParams[4]) ) );
                        break;

                    // Caso o usuário já terminou de criar...
                    case "Done":

                        doneCreating = true;
                        break;

                    // Se o usuário clicar no "x", apertar a Escape key ou clicar em cancelar, pergunta se ele quer criar mais objetos.
                    default:
                        if (Entradas.dialogoSimNao("Creating Process", "Would you like to create more objects?") == JOptionPane.NO_OPTION) {
                            doneCreating = true;
                        }
                        break;

                }
            
            // Trata exceção de número errado de parâmetros.
            } catch (ArgumentCountException e) {
                Saidas.dialogoErro("Object Creation Error", "Missing params!");
            
            // Trata exceção de posição inválida.
            } catch (InvalidPositionException e) {
                Saidas.dialogoErro("Object Creation Error", "Invalid Position!");
            
            // Trata exceção de ângulo inválido.
            } catch (InvalidBuckheadAngleException e) {
                Saidas.dialogoErro("Object Creation Error", "Invalid Buckhead Angle!");
                
                // Pergunta o usuário se quer criar mais algum objeto.
                if (Entradas.dialogoSimNao("Creation Process", "Would you like to create more objects?") == JOptionPane.NO_OPTION) {
                    doneCreating = true;
                }
                
            }
            
        }

        // 2a etapa
        // Movimento e checagem de colisão de projéteis.
        long lastTime, atualTime = System.currentTimeMillis();
        lastTime = atualTime;
        
        while (!ProjectilesList.isEmpty()) {

            atualTime = System.currentTimeMillis();
            // Mover ... (lastTime - atualTime)
            
            // Para cada projétil na lista...
            for (Projectile p : ProjectilesList) {
                // Move cada um...
                p.move(atualTime - lastTime);
                // e printa a posição dos vértices se necessário para correção.
                //p.printVertex();
            }
            
            // Atualiza o lastTime.
            lastTime = atualTime;

            // Para cada projétil na lista...
            for (Projectile p : ProjectilesList) {
                // Checa se colidiu com a área. Se sim, adiciona o projétil no caderninho da morte.
                if (p.CheckColision(area)) {
                    DeathNote.add(p);
                }
            }

            // Para cada projétil...
            for (Projectile p : ProjectilesList) {
                // E para cada anteparo...
                for (Bulkhead b : BulkheadList) {
                    // Se houve colisão entre eles e não está no caderninho da morte, adiciona o projétil à esta.
                    if (p.CheckColision(b) && !DeathNote.contains(p)) {
                            DeathNote.add(p);
                    }
                }
            }

            // Mais uma vez, para cada projétil da lista...
            for (Projectile p1 : ProjectilesList) {
                for (Projectile p2 : ProjectilesList) {
                    // Se p2 não for igual a p1...
                    if (!p1.equals(p2)) {
                        // Checa se p1 colidiu com p2.
                        if (p1.CheckColision(p2) && !DeathNote.contains(p1)) {
                            DeathNote.add(p1);
                        }
                    }
                }
            }

            // Percorre o caderninho da morte.
            for (Projectile p : DeathNote) {
                // E remove os objetos da lista de projéteis.
                ProjectilesList.remove(p);
            }
            
            try {
                // Dorme...
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}