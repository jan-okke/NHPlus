package datastorage;

/**
 * The DAO factory for creating DAO objects.
 */
public class DAOFactory {

    /**
     * The instance.
     */
    private static DAOFactory instance;

    /**
     * Private constructor for singleton pattern.
     */
    private DAOFactory() {

    }

    /**
     * Gets or creates an instance and then returns the instance.
     * @return The instance.
     */
    public static DAOFactory getDAOFactory() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    /**
     * Creates a TreatmentDAO.
     * @return The DAO.
     */
    public TreatmentDAO createTreatmentDAO() {
        return new TreatmentDAO(ConnectionBuilder.getConnection());
    }

    /**
     * Creates a PatientDAO.
     * @return The DAO.
     */
    public PatientDAO createPatientDAO() {
        return new PatientDAO(ConnectionBuilder.getConnection());
    }

    /**
     * Creates a LoginDAO.
     * @return The DAO.
     */
    public LoginDataDAO createLoginDAO() {
        return new LoginDataDAO(ConnectionBuilder.getConnection());
    }

    /**
     * Creates a CaregiverDAO.
     * @return The DAO.
     */
    public CaregiverDAO createCaregiverDAO() { return new CaregiverDAO(ConnectionBuilder.getConnection()); }
}