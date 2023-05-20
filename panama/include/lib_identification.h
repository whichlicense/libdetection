//#include <stdarg.h>
#include <stdbool.h>
#include <stdint.h>
//#include <stdlib.h>

typedef const char *(*CNormalizationFn)(const char*);

typedef struct FuzzyHashingConfig {
  const uint8_t *license_index;
  uintptr_t license_index_size;
  uintptr_t max_license_count;
  uint8_t confidence_threshold;
  bool exit_on_exact_match;
  CNormalizationFn normalization_fn;
} FuzzyHashingConfig;

typedef struct LicenseMatchEntry {
  const char *name;
  float confidence;
} LicenseMatchEntry;

typedef struct LicenseMatches {
  const struct LicenseMatchEntry *matches;
  uintptr_t length;
} LicenseMatches;

typedef struct GaoyaHashingConfig {
  const uint8_t *license_index;
  uintptr_t license_index_size;
  uintptr_t max_license_count;
  uintptr_t band_count;
  uintptr_t band_width;
  uintptr_t shingle_size;
  CNormalizationFn normalization_fn;
} GaoyaHashingConfig;

const char *fuzzy_compute_hash_default_normalization(const struct FuzzyHashingConfig *config,
                                                     const char *license);

const char *fuzzy_compute_hash(const struct FuzzyHashingConfig *config, const char *license);

struct LicenseMatches fuzzy_detect_license_default_normalization(const struct FuzzyHashingConfig *config,
                                                                 const char *license);

struct LicenseMatches fuzzy_detect_license(const struct FuzzyHashingConfig *config,
                                           const char *license);

const char *gaoya_compute_hash_default_normalization(const struct GaoyaHashingConfig *config,
                                                     const char *license);

const char *gaoya_compute_hash(const struct GaoyaHashingConfig *config, const char *license);

struct LicenseMatches gaoya_detect_license_default_normalization(const struct GaoyaHashingConfig *config,
                                                                 const char *license);

struct LicenseMatches gaoya_detect_license(const struct GaoyaHashingConfig *config,
                                           const char *license);
