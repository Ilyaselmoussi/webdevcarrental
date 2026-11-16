package ma.uir.awd.service.facade;
import ma.uir.awd.entity.Settings;

/** TVA exprimee en fraction (0.20 = 20%), frais fixes par jour de retard. */
public interface SettingsService {
    Settings getCurrent();
    Settings update(Long administratorId, double vatRate, double lateFeeRate);
}
