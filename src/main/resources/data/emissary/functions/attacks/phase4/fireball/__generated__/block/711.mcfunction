#built using mc-build (https://github.com/mc-build/mc-build)

execute facing entity @a[distance=..100,sort=random,gamemode=!spectator,tag=!emissarydebug,limit=1] feet run tp @s ~ ~ ~ ~ ~
tag @s add emissary
tag @s add attack
tag @s add fireball
tag @s add spell
tag @s add projectile
playsound nyctocosm:emissary.attack.fireball.shoot master @a[distance=..100] ~ ~ ~ 10