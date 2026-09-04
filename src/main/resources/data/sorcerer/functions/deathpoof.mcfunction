#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ^ ^3 ^-0.6 run particle squid_ink ~ ~ ~ 1 1 1 0 300
execute positioned ^ ^3 ^-0.6 run function effects:shockwave/transmittance_first
execute positioned ^ ^3 ^-0.6 run function effects:void_vein/explode
playsound nyctocosm:sorcerer.die.poof master @a[distance=..30] ~ ~ ~ 5