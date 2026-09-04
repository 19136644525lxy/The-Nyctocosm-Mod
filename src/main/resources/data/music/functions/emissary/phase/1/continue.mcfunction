#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set @s music.emissary.queue 3
playsound nyctocosm:boss.music.emissary.phase1.part1 music @s
scoreboard players operation @s musictimer = Emissary.Phase musictimer
tag @s add music.queuepopped