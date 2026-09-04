#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add music.sorcerer
scoreboard players set @s music.sorcerer.queue 12
playsound nyctocosm:boss.music.sorcerer.phase6.part1 music @s
scoreboard players operation @s musictimer = Sorcerer.Phase musictimer
tag @s add music.queuepopped