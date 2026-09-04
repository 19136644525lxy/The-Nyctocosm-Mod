#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add music.emissary
scoreboard players set @s music.emissary.queue 2
playsound nyctocosm:boss.music.emissary.phase1.part0 music @s
scoreboard players operation @s musictimer = Emissary.Phase musictimer
tag @s add music.queuepopped