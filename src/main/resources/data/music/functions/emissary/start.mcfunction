#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add music.emissary
scoreboard players set @s music.emissary.queue 1
playsound nyctocosm:boss.music.emissary.intro music @s
scoreboard players operation @s musictimer = Emissary.Intro musictimer