/*
 * HEIG-VD / SIO 
 * Ioannis Noukakis
 * Laboratoire N°01
 * File : RandomUniformGenerator.java
 */
package MAth;

import java.util.Random;

/**
 * Générateur de réalisations de variable aléatoire uniforme.
 * 
 * @author Ioannis Noukakis
 */
public class RandomUniformGenerator {
    private Random rand;

    public RandomUniformGenerator(long seed) {
        this.rand = new Random(seed);
    }
    
    /**
     * Génère une valeur uniformément distribué entre a et b 
     * (a inclus, b non inclus).
     * 
     * @param a : borne inférieur.
     * @param b : borne supérieur.
     * 
     * @return double
     */
    public double U(double a, double b)
    {
        if(a > b)
        {
            double temp = a;
            a = b;
            b = temp;
        }
        return a+rand.nextDouble()*(b-a);
    }
}
