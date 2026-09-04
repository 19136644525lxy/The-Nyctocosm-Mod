#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=music.sorcerer] run function music:sorcerer/subtick
execute as @s[tag=music.emissary] run function music:emissary/subtick
scoreboard players reset @s music.died
execute as @s[predicate=music:structures/crypt/in_crypt,tag=!music.crypt] run function music:structures/crypt/start
execute as @s[tag=music.crypt] run function music:structures/crypt/subtick