#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set @s music.sorcerer.queue 11
playsound nyctocosm:boss.music.sorcerer.phase6.part2 music @s
scoreboard players operation @s musictimer = Sorcerer.Phase musictimer
tag @s add music.queuepopped