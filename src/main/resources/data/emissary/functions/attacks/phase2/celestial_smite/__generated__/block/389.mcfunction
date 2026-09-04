#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove justsummoned
execute facing entity @a[sort=nearest,limit=1,gamemode=!spectator,tag=!emissarydebug] feet run tp @s ~ ~ ~ ~ ~
execute rotated ~ 0 run function effects:shockwave/meteor_shower
playsound nyctocosm:emissary.attack.celestial_smite.tear master @a[distance=..300] ~ ~ ~ 50