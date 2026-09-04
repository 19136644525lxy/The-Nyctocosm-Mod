#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[scores={music.sorcerer.queue=1},tag=!music.queuepopped] run function music:sorcerer/phase/1/start
execute as @s[scores={music.sorcerer.queue=2},tag=!music.queuepopped] run function music:sorcerer/phase/1/continue
execute as @s[scores={music.sorcerer.queue=3},tag=!music.queuepopped] run function music:sorcerer/phase/2/start
execute as @s[scores={music.sorcerer.queue=4},tag=!music.queuepopped] run function music:sorcerer/phase/2/continue
execute as @s[scores={music.sorcerer.queue=5},tag=!music.queuepopped] run function music:sorcerer/phase/3/start
execute as @s[scores={music.sorcerer.queue=6},tag=!music.queuepopped] run function music:sorcerer/phase/3/continue
execute as @s[scores={music.sorcerer.queue=7},tag=!music.queuepopped] run function music:sorcerer/phase/4/start
execute as @s[scores={music.sorcerer.queue=8},tag=!music.queuepopped] run function music:sorcerer/phase/4/continue
execute as @s[scores={music.sorcerer.queue=9},tag=!music.queuepopped] run function music:sorcerer/phase/5/start
execute as @s[scores={music.sorcerer.queue=10},tag=!music.queuepopped] run function music:sorcerer/phase/5/continue
execute as @s[scores={music.sorcerer.queue=11},tag=!music.queuepopped] run function music:sorcerer/phase/6/start
execute as @s[scores={music.sorcerer.queue=12},tag=!music.queuepopped] run function music:sorcerer/phase/6/continue
execute as @s[scores={music.sorcerer.queue=0},tag=!music.queuepopped] run function music:sorcerer/end/start
execute as @s[scores={music.sorcerer.queue=13},tag=!music.queuepopped] run function music:sorcerer/end/start
tag @s remove music.queuepopped