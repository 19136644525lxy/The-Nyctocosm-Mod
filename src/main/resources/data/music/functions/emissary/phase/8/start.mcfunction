#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add music.emissary
scoreboard players set @s music.emissary.queue 16
playsound nyctocosm:boss.music.emissary.phase4.part1 music @s
scoreboard players operation @s musictimer = Emissary.Phase musictimer
tag @s add music.queuepopped