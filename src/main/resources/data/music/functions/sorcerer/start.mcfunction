#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add music.sorcerer
scoreboard players set @s music.sorcerer.queue 1
playsound nyctocosm:boss.music.sorcerer.intro music @s
scoreboard players operation @s musictimer = Sorcerer.Intro musictimer