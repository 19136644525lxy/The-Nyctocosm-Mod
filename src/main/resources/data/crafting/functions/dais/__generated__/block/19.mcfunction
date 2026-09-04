#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s daisID = Tester daisID
execute positioned ~ ~ ~ run function crafting:dais/effects/summon_ring
execute positioned ~ ~-1 ~ run function crafting:dais/effects/summon_anvil
execute positioned ~ ~-1 ~ run function crafting:dais/summon_trader
playsound nyctocosm:dais.construct master @a[distance=..30] ~ ~ ~ 5
tag @s remove justsummoned