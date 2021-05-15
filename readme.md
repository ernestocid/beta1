# README

Here you will find the code for the BETA test generation tool. More information about the tool can be found on the website [www.beta-tool.info](http://www.beta-tool.info/).

## Setting up development environment

- We use Gradle to build the project. So you should download it from their [website](http://www.gradle.org/) and install it before you proceed.
- Clone the repository main folder (the folder which contains all the subprojects).
- Using the terminal switch to the main project folder and run:
- 		gradle build
- This command will download all dependencies, build and test the projects. Then run:
- 		gradle eclipse
- This will build the eclipse files so you can import the projects on eclipse.
- Open eclipse then go to File->Import->General->Existing Projects into Workspace, click on Next. 
- On 'Select root directory' browse for the project main folder.
- On the 'Options' section make sure the checkbox 'Search for nested projects' is checked. This will show all the subprojects of the main folder.
- Finally, select all the subprojects (don't select the main project folder) and click on 'Finish'

# License

Copyright (c) 2014, Ernesto C. B. de Matos, Anamaria M. Moreira, João B. de S. Neto.
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

