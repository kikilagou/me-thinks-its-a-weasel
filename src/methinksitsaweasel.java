import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class methinksitsaweasel {

    protected static char[] target = "methinks it is like a weasel".toCharArray();
    protected static int len = 28;

    public methinksitsaweasel() {
         
    }

    /**
     * STEP 1: Representing and initialising individuals
     *
     * Method creates a char array of length 28 and populates it with random ascii characters
     */
    protected static char[] generateIndividual() {
        Random random = new Random();
        char[] indivudual = new char[len];

        for (int i = 0; i < len; i++) {
            indivudual[i] = (char) (random.nextInt(127 - 32 + 1) + 32);
        }
        return indivudual;
    }

    /**
     * STEP 2: Evaluating Individuals
     * 
     * Goes through generated individual and target character by character and sees if they agree
     */
    protected static int evalutateIndividual(char[] target, char[] indivudual) {
        int counter = 0;
        for (int i = 0; i < len; i++) {
            if(target[i] == indivudual[i]) {
                counter++;
            }
        }
//        System.out.println(new String(indivudual) + ", fit = " + counter);
        return counter;
    }


    /**
     * STEP 3: Mutation
     * 
     * Returns mutant version of individual
     * 
     * The Math.random bit might be wrong...
     */
    protected static char[] mutate(char[] individual) {
        Random random = new Random();
        char[] mutatedIndividual = new char[len] ;
        
        for (int i = 0; i < len; i++) {
            if(new Random().nextInt(len)==0) {
                mutatedIndividual[i] = (char) (random.nextInt(127 - 32 + 1) + 32);
            } else {
                mutatedIndividual[i] = individual[i];
            }
        }
        
//        System.out.println(new String(mutatedIndividual));
//        System.out.println(new String(individual));
        
        return mutatedIndividual;
        
    }

    /**
     * STEP 4.1: Mutation hill climber
     * 
     * Making a simple mutation hill climber 
     */
    protected static String hillClimber() {
        char[] A = generateIndividual();

        while(evalutateIndividual(target, A) != len) {
            char[] B = mutate(A);

            if (evalutateIndividual(target, B) > evalutateIndividual(target, A)) {
                A = B;
            }
        }
        return new String(A);
    }

    protected static char[][] generatePopultation(int numberOfIndividuals) {
        char[][] pop = new char[28][];

        for (int i = 0; i < len; i++) {
                pop[i] = generateIndividual();
        }
        return pop;
    }

    protected static int populationFitness(char[][] pop) {
        int maxFitness = 0;
        int fitness;

        for (int i = 0; i < pop.length; i++) {
            fitness = evalutateIndividual(target, pop[i]);
            if(fitness > maxFitness) {
                maxFitness = fitness;
            }
        }
        System.out.println("fitness: " + maxFitness);
        return maxFitness;
    }

    /**
     * STEP 4.2: A GA without crossover
     * 
     */
    protected static void noCrossover() {

        char[][] pop = generatePopultation(10);
        char[] parent1;
        char[] child;
        char[] A;
        char[] B;

        System.out.println("-----------------------------------------");

        while(populationFitness(pop) != len) {
            A = pop[new Random().nextInt(pop.length)];
            B = pop[new Random().nextInt(pop.length)];
//            System.out.println("A " + new String(A));
//            System.out.println("B " + new String(B));


            if(evalutateIndividual(target, A) > evalutateIndividual(target, B)) {
                parent1 = A;
            } else {
                parent1 = B;
            }

//            System.out.println("parent1 " + new String(parent1));

            child = mutate(parent1);
//            System.out.println("child " + new String(child));

            A = pop[new Random().nextInt(pop.length)];
            B = pop[new Random().nextInt(pop.length)];
//            System.out.println("A2 " + new String(A));
//            System.out.println("B2 " + new String(B));

            if(evalutateIndividual(target, A) > evalutateIndividual(target, B)) {
                B = child;
            } else {
                A = child;
            }

//            System.out.println("A3 " + new String(A));
//            System.out.println("B3 " + new String(B));

        }

    }


    /**
     * STEP 5: Crossover
     * 
     */
    protected static char[] crossover(char[] parent1, char[] parent2) {

        char[] child = new char[28];
        for(int i = 0; i < len; i++) {
            if(Math.random() < 0.5) {
                 child[i] = parent1[i];
            } else {
                child[i] = parent2[i];
            }
        }
        
        return child;
    }
    

    /**
     * STEP 6: A GA with crossover
     *
     */
    protected static void withCrossover() {
        ArrayList pop = new ArrayList();

        for(int i = 0; i < 499; i++) {
            pop.add(generateIndividual());
        }

        boolean maxFitnessFound = false;
        while(maxFitnessFound != true) {

            // choose parent 1
            char[] A = (char[]) pop.get(new Random().nextInt(pop.size()));
            char[] B = (char[]) pop.get(new Random().nextInt(pop.size()));
            
            char[] parent1;
            if(evalutateIndividual(target, A) > evalutateIndividual(target, B)) {
                parent1 = A;
            } else {
                parent1 = B;
            }

            // choose parent 2
            A = (char[]) pop.get(new Random().nextInt(pop.size()));
            B = (char[]) pop.get(new Random().nextInt(pop.size()));

            char[] parent2;
            if(evalutateIndividual(target, A) > evalutateIndividual(target, B)) {
                parent2 = A;
            } else {
                parent2 = B;
            }
            
            // create child 
            char[] result_of_crossover = crossover(parent1, parent2);
            char[] child = mutate(result_of_crossover); 
            
            // choose an individual to be replaced
            A = (char[]) pop.get(new Random().nextInt(pop.size()));
            B = (char[]) pop.get(new Random().nextInt(pop.size()));

            if(evalutateIndividual(target, A) > evalutateIndividual(target, B)) {
                B = child;
            } else {
                A = child;
            }

        }
    }
    

    public static void main(String[] args) {
        //System.out.print(new String(generateIndividual()));
//        evalutateIndividual(target, generateIndividual());
//        mutate(generateIndividual());
//        hillClimber();
        noCrossover();

    }
}
