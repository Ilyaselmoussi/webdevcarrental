package ma.uir.awd.service.impl;

import ma.uir.awd.dao.AdministratorRepository;
import ma.uir.awd.dao.SettingsRepository;
import ma.uir.awd.entity.Administrator;
import ma.uir.awd.entity.Settings;
import ma.uir.awd.service.facade.SettingsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SettingsServiceImpl implements SettingsService {
    private final SettingsRepository settings;
    private final AdministratorRepository administrators;

    public SettingsServiceImpl(SettingsRepository settings, AdministratorRepository administrators) {
        this.settings = settings;
        this.administrators = administrators;
    }

    @Override public Settings getCurrent() {
        return settings.findAll().stream().findFirst().orElseThrow(() ->
                new IllegalStateException("Configurer la TVA et les penalites avant la facturation"));
    }

    @Override @Transactional
    public Settings update(Long administratorId, double vatRate, double lateFeeRate) {
        if (!Double.isFinite(vatRate) || vatRate < 0 || vatRate > 1
                || !Double.isFinite(lateFeeRate) || lateFeeRate < 0) {
            throw new IllegalArgumentException("TVA fractionnaire 0..1 et frais/jour positifs requis");
        }
        Administrator administrator = administrators.findById(administratorId).orElseThrow(() ->
                new IllegalArgumentException("Administrateur introuvable"));
        Settings record = settings.findByAdministrator_Id(administratorId).orElseGet(Settings::new);
        record.setAdministrator(administrator);
        record.setVatRate(vatRate);
        record.setLateFeeRate(lateFeeRate);
        return settings.save(record);
    }
}
