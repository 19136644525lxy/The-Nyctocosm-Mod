#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set @s music.emissary.queue 13
playsound nyctocosm:boss.music.emissary.phase3.part4 music @s
scoreboard players operation @s musictimer = Emissary.Phase musictimer
tag @s add music.queuepopped